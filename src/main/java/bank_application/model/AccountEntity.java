package bank_application.model;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;


@Data
@Entity
@Table(name = "Account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Balance")
    private BigDecimal balance;

    @Column(name = "Type_currency")
    private String type_currency;

    public AccountEntity() {
    }
}
