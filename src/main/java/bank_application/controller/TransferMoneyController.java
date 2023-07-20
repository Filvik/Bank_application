package bank_application.controller;

import bank_application.model.BalanceResponse;
import bank_application.service.AccountService;
import bank_application.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class TransferMoneyController {

    private final TransferService transferService;
    private static final Logger log = LogManager.getLogger(AccountService.class);
    @PutMapping(value = "/TransferMoney_for/{idSender}/{idRecipient}/{sum}")
    @Operation(summary = "Запрос на перевод", tags = "Контроллер трансфера денежной суммы.")
    public BalanceResponse transferMoney(@PathVariable @Parameter(description = "Идентификатор пользователя отправителя") long idSender,
                                         @PathVariable @Parameter(description = "Идентификатор пользователя получателя") long idRecipient,
                                     @PathVariable @Parameter(description = "Сумма") BigDecimal sum) {
        log.info("Вызван метод transferMoney с idSender = " + idSender + "c idRecipient = " + idRecipient + " и запрашиваемой суммой = " + sum);
        return transferService.transferMoney(idSender, idRecipient, sum);
    }
}
