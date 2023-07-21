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
import java.util.Objects;

@Service
public class TransferService {

    private static final Logger log = LogManager.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final OperationsRepository operationsRepository;

    public TransferService(AccountRepository accountRepository, OperationsRepository operationsRepository) {
        this.accountRepository = accountRepository;
        this.operationsRepository = operationsRepository;
        log.info("Проинициализированы экземпляры класса AccountRepository и OperationsRepository в классе TransferService.");
    }

    /**
     * Метод проверяет есть ли в БД пользователи с такими ID,
     * корректна ли сумма перевода, идентичны ли типы валюты.
     *
     * @param idSender    ID отправителя.
     * @param idRecipient ID получателя.
     * @param sum         Сумма перевода.
     * @return Объект класса BalanceResponse.
     */
    public BalanceResponse transferMoney(long idSender, long idRecipient, BigDecimal sum) {

        BalanceResponse balanceResponse = new BalanceResponse();

        if (sum.compareTo(BigDecimal.valueOf(0)) > 0) {
            if (idSender != idRecipient) {
                try {
                    accountRepository.findById(idSender).ifPresentOrElse(idS -> {
                                if (accountRepository.findById(idRecipient).isPresent()) {

                                    AccountEntity accountEntityForRecipient = accountRepository.findById(idRecipient).get();

                                    if (Objects.equals(idS.getTypeCurrency(),
                                            accountEntityForRecipient.getTypeCurrency())) {
                                        operation(idS, accountEntityForRecipient, sum, balanceResponse);
                                    } else {
                                        balanceResponse.setValue(BigDecimal.valueOf(0));
                                        balanceResponse.setDescription("Разные типы валют!Перевод не допустим!");
                                    }
                                } else {
                                    balanceResponse.setValue(BigDecimal.valueOf(0));
                                    balanceResponse.setDescription("Отсутствует получатель!");
                                }
                            },
                            () -> {
                                balanceResponse.setValue(BigDecimal.valueOf(0));
                                balanceResponse.setDescription("Отсутствует отправитель!");
                            });
                } catch (Exception exception) {
                    log.error("Внутренняя ошибка сервера: " + exception);
                }

            } else {
                balanceResponse.setValue(BigDecimal.valueOf(0));
                balanceResponse.setDescription("Отправитель и получатель не могут иметь одинаковый ID!");
            }
            return balanceResponse;
        } else {
            balanceResponse.setValue(BigDecimal.valueOf(0));
            balanceResponse.setDescription("Некорректная сумма перевода!");
            return balanceResponse;
        }
    }

    /**
     * Метод выполняет операцию изменения баланса у двух объектов в таблице Account и создание нового объекта в таблице Operations.
     *
     * @param idSender    Объект класса AccountEntity(Отправитель).
     * @param idRecipient Объект класса AccountEntity(Получатель).
     * @param sum         Сумма перевода.
     */
    @Transactional
    public void operation(AccountEntity idSender, AccountEntity idRecipient, BigDecimal sum, BalanceResponse balanceResponse) {

        if (idSender.getBalance().compareTo(sum) >= 0) {

            idSender.setBalance(idSender.getBalance().subtract(sum));
            idRecipient.setBalance(idRecipient.getBalance().add(sum));
            accountRepository.saveAndFlush(idSender);
            accountRepository.saveAndFlush(idRecipient);

            balanceResponse.setValue(BigDecimal.valueOf(1));
            TypeOperationEntity typeOperationEntity = new TypeOperationEntity();
            typeOperationEntity.setId(3);

            OperationsEntity operationsEntity = new OperationsEntity(idSender, idRecipient, typeOperationEntity,
                    sum, new java.util.Date().getTime());
            operationsRepository.saveAndFlush(operationsEntity);
        } else {
            balanceResponse.setValue(BigDecimal.valueOf(0));
            balanceResponse.setDescription("Превышена возможная сумма перевода!");
        }
    }
}
