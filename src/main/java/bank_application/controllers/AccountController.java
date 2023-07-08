package bank_application.controllers;

import bank_application.model.BalanceResponse;
import bank_application.service.BankService;
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
    public BalanceResponse getBalance(@PathVariable("id") long id) {
        log.info("Вызван метод getBalance с id = "+ id);
        return bankService.howMuchMoney(id);
    }

    @PutMapping(value = "/takeMoney_for_id/{id}/{sum}")
    public BalanceResponse takeMoney(@PathVariable("id") long id , @PathVariable("sum") BigDecimal sum) {
        log.info("Вызван метод takeMoney с id = "+ id + " и запрашиваемой суммой = " + sum);
        return bankService.takeMoney(id,sum);
    }

    @PutMapping(value = "/putMoney_for_id/{id}/{sum}")
    public BalanceResponse putMoney(@PathVariable("id") long id , @PathVariable("sum") BigDecimal sum) {
        log.info("Вызван метод putMoney с id = "+ id + " и запрашиваемой суммой = " + sum);
        return bankService.putMoney(id,sum);
    }
}
