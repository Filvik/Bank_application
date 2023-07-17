package bank_application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "Список операций")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OperationListResponse {

    @JsonProperty("DATA")
    @Schema(description = "Дата операции")
    private long dateTimeStamp;
    @JsonProperty("TYPE_OPERATION")
    @Schema(description = "Тип операции")
    private int typeOperation;
    @JsonProperty("SUM")
    @Schema(description = "Сумма операции")
    private BigDecimal sum;
    @JsonProperty("DESCRIPTION")
    @Schema(description = "Ошибка при вводе ID пользователя")
    private String description;

    public OperationListResponse() {
    }
}
