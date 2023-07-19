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

    @Column(name = "BALANCE", nullable = false)
    @Schema(description = "Баланс")
    private BigDecimal balance;

    @Column(name = "TYPE_CURRENCY", nullable = false)
    @Schema(description = "Тип валюты")
    private String typeCurrency;

    public AccountEntity() {
    }
}
