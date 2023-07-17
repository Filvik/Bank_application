package bank_application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Ответа на запрос")
public class BalanceResponse {
    @JsonProperty("value")
    @Schema(description = "При запросе баланса - величина баланса, при других операциях - значения результативности операции")
    private BigDecimal value;
    @JsonProperty("description_error")
    @Schema(description = "Текст возникшей ошибки")
    private String description;
    @JsonProperty("type_currency")
    @Schema(description = "Тип валюты")
    private String typeCurrency;

    public BalanceResponse(int value, String description) {
        this.value = BigDecimal.valueOf(value);
        this.description = description;
    }
    public BalanceResponse(BigDecimal value,String typeCurrency) {
        this.value = value;
        this.typeCurrency = typeCurrency;
    }
    public BalanceResponse(BigDecimal value) {
        this.value = value;
    }
}
