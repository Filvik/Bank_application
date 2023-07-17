package bank_application.service;

import bank_application.model.OperationListResponse;
import bank_application.model.OperationsEntity;
import bank_application.repository.AccountRepository;
import bank_application.repository.OperationsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationsService {

    private static final Logger log = LogManager.getLogger(BankService.class);
    private final AccountRepository accountRepository;
    private final OperationsRepository operationsRepository;

    public OperationsService(AccountRepository accountRepository, OperationsRepository operationsRepository) {
        this.accountRepository = accountRepository;
        this.operationsRepository = operationsRepository;
        log.info("Проинициализированы экземпляры класса AccountRepository и OperationsRepository в классе OperationsService.");
    }

    /**
     * Метод проверяет есть ли в БД пользователь с таким ID и возвращает все его операции за выбранный период времени, в
     * хронологической последовательности.
     *
     * @param id        ID клиента.
     * @param dateStart Начало периода.
     * @param dateStop  Конец периода.
     * @return Коллекцию класса OperationListResponse.
     */
    public List<OperationListResponse> getOperationList(long id, Long dateStart, Long dateStop) {

        List<OperationListResponse> operationListResponses = new ArrayList<>();
        OperationListResponse operationResponseError = new OperationListResponse();

        try {
            if (accountRepository.findById(id).isPresent()) {

                if (dateStart == null) {
                    dateStart = 0L;
                }
                if (dateStop == null) {
                    dateStop = 9999999999999L;
                }

                if (dateStart > dateStop) {
                    operationResponseError.setDescription("Некорректно задан временной диапазон!");
                    operationListResponses.add(operationResponseError);
                    return operationListResponses;
                }

                List<OperationsEntity> operationsEntities = operationsRepository.findAllByIdAccountAndDataTimeStampBetweenOrderByDataTimeStamp(
                        accountRepository.findById(id).get(), dateStart, dateStop);

                for (OperationsEntity oe : operationsEntities) {
                    OperationListResponse operationResponse = new OperationListResponse();
                    operationResponse.setSum(oe.getSum());
                    operationResponse.setDateTimeStamp(oe.getDataTimeStamp());
                    operationResponse.setTypeOperation(oe.getTypeOperationEntity().getId());
                    operationListResponses.add(operationResponse);
                }
                return operationListResponses;
            }
        } catch (Exception exception) {
            log.error("Внутренняя ошибка сервера: " + exception);
        }
        operationResponseError.setDescription("Ошибка при вводе ID пользователя!");
        operationListResponses.add(operationResponseError);
        return operationListResponses;
    }
}
