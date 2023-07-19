package bank_application.controller;

import bank_application.model.OperationListResponse;
import bank_application.service.AccountService;
import bank_application.service.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class OperationController {

    private final OperationsService operationsService;
    private static final Logger log = LogManager.getLogger(AccountService.class);

    @GetMapping(value = "/getOperationList_for_id/{id}")
    @Operation(summary = "Запрос списка операций", tags = "Контроллер получение списка операций за определенный период времени")
    public List<OperationListResponse> getOperationList(@PathVariable @Parameter(description = "Идентификатор пользователя") long id,
                                                        @RequestParam(required = false) @Parameter(description = "Дата начала периода") Long dataStart,
                                                        @RequestParam(required = false) @Parameter(description = "Дата окончание периода") Long dataStop) {
        log.info("Вызван метод getOperationList с id = " + id + "дата начала: " + dataStart + " дата окончания: " + dataStop);
        return operationsService.getOperationList(id, dataStart, dataStop);
    }
}
