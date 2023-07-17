package bank_application.controller;

import bank_application.model.BalanceResponse;
import bank_application.model.OperationListResponse;
import bank_application.service.BankService;
import bank_application.service.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AccountController {

    private final BankService bankService;
    private final OperationsService operationsService;
    private static final Logger log = LogManager.getLogger(BankService.class);

    @GetMapping(value = "/getBalance_for_id/{id}")
    @Operation(summary = "Запрос баланса", tags = "Контроллер получение баланса")
    public BalanceResponse getBalance(@PathVariable @Parameter(description = "Идентификатор пользователя") long id) {
        log.info("Вызван метод getBalance с id = " + id);
        return bankService.howMuchMoney(id);
    }

    @PutMapping(value = "/takeMoney_for_id/{id}/{sum}")
    @Operation(summary = "Запрос на снятие", tags = "Контроллер запроса суммы на снятие")
    public BalanceResponse takeMoney(@PathVariable @Parameter(description = "Идентификатор пользователя") long id,
                                     @PathVariable @Parameter(description = "Сумма") BigDecimal sum) {
        log.info("Вызван метод takeMoney с id = " + id + " и запрашиваемой суммой = " + sum);
        return bankService.takeMoney(id, sum);
    }

    @RequestMapping(value = "/putMoney_for_id/{id}/{sum}", method = RequestMethod.PUT)
    @Operation(summary = "Запрос на пополнение", tags = "Контроллер пополнения счета")
    public BalanceResponse putMoney(@PathVariable @Parameter(description = "Идентификатор пользователя") long id,
                                    @PathVariable @Parameter(description = "Сумма") BigDecimal sum) {
        log.info("Вызван метод putMoney с id = " + id + " и запрашиваемой суммой = " + sum);
        return bankService.putMoney(id, sum);
    }

    @GetMapping(value = "/getOperationList_for_id/{id}")
    @Operation(summary = "Запрос списка операций", tags = "Контроллер получение списка операций за определенный период времени")
    public List<OperationListResponse> getOperationList(@PathVariable @Parameter(description = "Идентификатор пользователя") long id,
                                                        @RequestParam(required = false) @Parameter(description = "Дата начала периода") Long dataStart,
                                                        @RequestParam(required = false) @Parameter(description = "Дата окончание периода") Long dataStop) {
        log.info("Вызван метод getOperationList с id = " + id);
        return operationsService.getOperationList(id, dataStart, dataStop);
    }
}
