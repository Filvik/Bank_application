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
public class AccountService {
    private static final Logger log = LogManager.getLogger(AccountService.class);
    private static final int OPERATION_TAKE = 1;
    private static final int OPERATION_PUT = 2;
    private final AccountRepository accountRepository;
    private final OperationsRepository operationsRepository;

    public AccountService(AccountRepository accountRepository, OperationsRepository operationsRepository) {
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

        AtomicReference<BalanceResponse> balanceResponse = new AtomicReference<>();
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
    public BalanceResponse takeMoney(long id, BigDecimal sum) {

        if (sum.compareTo(BigDecimal.valueOf(0)) <= 0) {
            return new BalanceResponse(0, "Некорректное значение запрошенной суммы!");
        }
        BalanceResponse balanceResponse = new BalanceResponse();
        try {
            accountRepository.findById(id).ifPresentOrElse(accountEntity -> {

                        if (sum.compareTo(accountEntity.getBalance()) <= 0) {
                                operation(accountEntity, sum, OPERATION_PUT, balanceResponse);
                        } else {
                            balanceResponse.setValue(BigDecimal.valueOf(0));
                            balanceResponse.setDescription("Недостаточно средств на счёте!");
                        }},
                    () -> {
                        balanceResponse.setValue(BigDecimal.valueOf(0));
                        balanceResponse.setDescription("Отсутствует данный пользователь!");
                    });
            return balanceResponse;
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
    public BalanceResponse putMoney(long id, BigDecimal sum) {

        if (sum.compareTo(BigDecimal.valueOf(0)) <= 0) {
            return new BalanceResponse(0, "Некорректное значение суммы пополнения!");
        }
        BalanceResponse balanceResponse = new BalanceResponse();
        try {
            accountRepository.findById(id).ifPresentOrElse(accountEntity -> {
                            operation(accountEntity, sum, OPERATION_TAKE, balanceResponse);
                    },
                    () -> {
                        balanceResponse.setValue(BigDecimal.valueOf(0));
                        balanceResponse.setDescription("Отсутствует данный пользователь!");
                    });
            return balanceResponse;
        } catch (Exception exception) {
            log.error("Внутренняя ошибка сервера: " + exception);
            return new BalanceResponse(0, "Внутренняя ошибка сервера!");
        }
    }

    /**
     * Метод выполняет операцию изменения баланса в таблице Account и создание нового объекта в таблице Operations.
     *
     * @param accountEntity  Объект класса AccountEntity.
     * @param operationType  Тип операции.
     * @param sum Сумма пополнения.
     */
    @Transactional
    public void operation(AccountEntity accountEntity, BigDecimal sum, int operationType, BalanceResponse balanceResponse){

        if (operationType == OPERATION_TAKE) {
            accountEntity.setBalance(accountEntity.getBalance().add(sum));
        }
        if (operationType == OPERATION_PUT) {
            accountEntity.setBalance(accountEntity.getBalance().subtract(sum));
        }
        accountRepository.saveAndFlush(accountEntity);
        balanceResponse.setValue(BigDecimal.valueOf(1));

        TypeOperationEntity typeOperationEntity = new TypeOperationEntity();
        typeOperationEntity.setId(operationType);

        OperationsEntity operationsEntity = new OperationsEntity(accountEntity, typeOperationEntity,
                sum, new java.util.Date().getTime());
        operationsRepository.saveAndFlush(operationsEntity);
    }
}
