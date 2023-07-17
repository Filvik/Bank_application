package bank_application.service;

import bank_application.model.AccountEntity;
import bank_application.model.BalanceResponse;
import bank_application.model.OperationsEntity;
import bank_application.model.TypeOperationEntity;
import bank_application.repository.AccountRepository;
import bank_application.repository.OperationsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BankService {
    private static final Logger log = LogManager.getLogger(BankService.class);
    private final AccountRepository accountRepository;
    private final OperationsRepository operationsRepository;
    AtomicReference<BalanceResponse> balanceResponse = new AtomicReference<>();


    public BankService(AccountRepository accountRepository, OperationsRepository operationsRepository) {
        this.accountRepository = accountRepository;
        this.operationsRepository = operationsRepository;
        log.info("Проинициализированы экземпляры класса AccountRepository и OperationsRepository в классе BankService.");
    }

    /**
     * Метод проверяет есть ли в БД пользователь с таким ID и в зависимости от этого, возвращает либо баланс,
     * либо что пользователь отсутствует.
     *
     * @param id ID клиента.
     * @return Объект класса BalanceResponse.
     */
    public BalanceResponse howMuchMoney(long id) {

        try {
            accountRepository.findById(id).ifPresentOrElse(fid ->
                            balanceResponse.set(new BalanceResponse(fid.getBalance(), fid.getTypeCurrency())),
                    () -> balanceResponse.set(new BalanceResponse(-1, "Отсутствует данный пользователь!")));
            return balanceResponse.get();
        } catch (Exception exception) {
            log.error("Внутренняя ошибка сервера: " + exception);
            return new BalanceResponse(-1, "Внутренняя ошибка сервера!");
        }
    }

    /**
     * Метод проверяет есть ли в БД пользователь с таким ID, имеет ли он достаточно денег на своём балансе,
     * корректна ли запрошенная им сумма для снятия.
     *
     * @param id  ID клиента.
     * @param sum Запрашиваемая сумма.
     * @return Объект класса BalanceResponse.
     */
    @Transactional
    public BalanceResponse takeMoney(long id, BigDecimal sum) {

        if (sum.compareTo(BigDecimal.valueOf(0)) <= 0) {
            return new BalanceResponse(0, "Некорректное значение запрошенной суммы!");
        }
        try {
            accountRepository.findById(id).ifPresentOrElse(fid -> {
                        if (sum.compareTo(fid.getBalance()) <= 0) {
                            fid.setBalance(fid.getBalance().subtract(sum));
                            accountRepository.save(fid);
                            accountRepository.flush();
                            balanceResponse.set(new BalanceResponse(BigDecimal.valueOf(1)));

                            OperationsEntity operationsEntity = new OperationsEntity();
                            TypeOperationEntity typeOperationEntity = new TypeOperationEntity();
                            typeOperationEntity.setId(2);
                            AccountEntity accountEntity = new AccountEntity();
                            accountEntity.setId(id);

                            operationsEntity.setTypeOperationEntity(typeOperationEntity);
                            operationsEntity.setIdAccount(accountEntity);
                            operationsEntity.setSum(sum);
                            operationsEntity.setDataTimeStamp(new java.util.Date().getTime());
                            operationsRepository.save(operationsEntity);
                            operationsRepository.flush();

                        } else {
                            balanceResponse.set(new BalanceResponse(0, "Недостаточно средств на счёте!"));
                        }
                    },
                    () -> balanceResponse.set(new BalanceResponse(0, "Отсутствует данный пользователь!")));
            return balanceResponse.get();
        } catch (Exception exception) {
            log.error("Внутренняя ошибка сервера: " + exception);
            return new BalanceResponse(0, "Внутренняя ошибка сервера!");
        }
    }

    /**
     * Метод проверяет есть ли в БД пользователь с таким ID,
     * корректна ли пополняемая им сумма.
     *
     * @param id  ID клиента.
     * @param sum Сумма пополнения.
     * @return Объект класса BalanceResponse.
     */
    @Transactional
    public BalanceResponse putMoney(long id, BigDecimal sum) {

        if (sum.compareTo(BigDecimal.valueOf(0)) <= 0) {
            return new BalanceResponse(0, "Некорректное значение суммы пополнения!");
        }
        try {
            accountRepository.findById(id).ifPresentOrElse(fid -> {
                        fid.setBalance(fid.getBalance().add(sum));
                        accountRepository.save(fid);
                        accountRepository.flush();
                        balanceResponse.set(new BalanceResponse(BigDecimal.valueOf(1)));

                        OperationsEntity operationsEntity = new OperationsEntity();
                        TypeOperationEntity typeOperationEntity = new TypeOperationEntity();
                        typeOperationEntity.setId(1);
                        AccountEntity accountEntity = new AccountEntity();
                        accountEntity.setId(id);

                        operationsEntity.setTypeOperationEntity(typeOperationEntity);
                        operationsEntity.setIdAccount(accountEntity);
                        operationsEntity.setSum(sum);
                        operationsEntity.setDataTimeStamp(new java.util.Date().getTime());
                        operationsRepository.save(operationsEntity);
                        operationsRepository.flush();
                    },
                    () -> balanceResponse.set(new BalanceResponse(0, "Отсутствует данный пользователь!")));
            return balanceResponse.get();
        } catch (Exception exception) {
            log.error("Внутренняя ошибка сервера: " + exception);
            return new BalanceResponse(0, "Внутренняя ошибка сервера!");
        }
    }
}
