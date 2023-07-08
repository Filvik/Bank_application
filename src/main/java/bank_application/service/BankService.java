package bank_application.service;

import bank_application.model.BalanceResponse;
import bank_application.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BankService {
    private static final Logger log = LogManager.getLogger(BankService.class);
    private final AccountRepository accountRepository;
    AtomicReference<BalanceResponse> balanceResponse = new AtomicReference<>();

    public BankService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        log.info("Создан экземпляр класса AccountRepository.");
    }

    /**
     * Метод проверяет есть ли в БД пользователь с таким ID и в зависимости от этого, возвращает либо баланс,
     * либо что пользователь отсутствует.
     * @param id ID клиента.
     * @return Объект класса BalanceResponse
     */
    public BalanceResponse howMuchMoney(long id) {

        try {
            accountRepository.findById(id).ifPresentOrElse(fid ->
                            balanceResponse.set(new BalanceResponse(fid.getBalance(), fid.getType_currency())),
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
     * @param id ID клиента.
     * @param sum Запрашиваемая сумма.
     * @return Объект класса BalanceResponse
     */
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
     * @param id ID клиента.
     * @param sum Сумма пополнения.
     * @return Объект класса BalanceResponse
     */
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
                    },
                    () -> balanceResponse.set(new BalanceResponse(0, "Отсутствует данный пользователь!")));
            return balanceResponse.get();
        } catch (Exception exception) {
            log.error("Внутренняя ошибка сервера: " + exception);
            return new BalanceResponse(0, "Внутренняя ошибка сервера!");
        }
    }
}
