package bank_application.controller;

import bank_application.model.BalanceResponse;
import bank_application.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AccountController {

    private final BankService bankService;
    private static final Logger log = LogManager.getLogger(BankService.class);

    @GetMapping(value = "/getBalance_for_id/{id}")
    @Operation(summary = "Запрос баланса", tags = "Контроллер получение баланса")
    public BalanceResponse getBalance(@PathVariable@Parameter(description = "Идентификатор пользователя") long id) {
        log.info("Вызван метод getBalance с id = "+ id);
        return bankService.howMuchMoney(id);
    }

    @PutMapping(value = "/takeMoney_for_id/{id}/{sum}")
    @Operation(summary = "Запрос на снятие", tags = "Контроллер запроса суммы на снятие")
    public BalanceResponse takeMoney(@PathVariable@Parameter(description = "Идентификатор пользователя") long id ,
                                     @PathVariable@Parameter(description = "Сумма") BigDecimal sum) {
        log.info("Вызван метод takeMoney с id = "+ id + " и запрашиваемой суммой = " + sum);
        return bankService.takeMoney(id,sum);
    }

    @RequestMapping(value = "/putMoney_for_id/{id}/{sum}", method = RequestMethod.PUT)
    @Operation(summary = "Запрос на пополнение", tags = "Контроллер пополнения счета")
    public BalanceResponse putMoney(@PathVariable@Parameter(description = "Идентификатор пользователя") long id ,
                                    @PathVariable@Parameter(description = "Сумма") BigDecimal sum) {
        log.info("Вызван метод putMoney с id = "+ id + " и запрашиваемой суммой = " + sum);
        return bankService.putMoney(id,sum);
    }
}
