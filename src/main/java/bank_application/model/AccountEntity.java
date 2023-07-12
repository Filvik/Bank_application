package bank_application.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ACCOUNT")
@Schema(name = "Аккаунт пользователя")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор")
    private Long id;

    @Column(name = "BALANCE")
    @Schema(description = "Баланс")
    private BigDecimal balance;

    @Column(name = "TYPE_CURRENCY")
    @Schema(description = "Тип валюты")
    private String type_currency;

    public AccountEntity() {
    }
}
