package bank_application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceResponse {
    @JsonProperty("value")
    private BigDecimal value;
    @JsonProperty("description_error")
    private String description;
    @JsonProperty("type_currency")
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
