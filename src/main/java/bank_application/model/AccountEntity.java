package bank_application.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Account")
@Schema(name = "Аккаунт пользователя")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор")
    private Long id;

    @Column(name = "Balance")
    @Schema(description = "Баланс")
    private BigDecimal balance;

    @Column(name = "Type_currency")
    @Schema(description = "Тип валюты")
    private String type_currency;

    public AccountEntity() {
    }
}
