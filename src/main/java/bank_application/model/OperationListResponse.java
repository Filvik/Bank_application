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

    @JsonProperty("data")
    @Schema(description = "Дата операции")
    private long dateTimeStamp;
    @JsonProperty("type_operation")
    @Schema(description = "Тип операции")
    private int typeOperation;
    @JsonProperty("sum")
    @Schema(description = "Сумма операции")
    private BigDecimal sum;
    @JsonProperty("description")
    @Schema(description = "Ошибка при вводе ID пользователя")
    private String description;

    public OperationListResponse() {
    }
}
