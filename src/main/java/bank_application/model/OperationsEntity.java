package bank_application.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "OPERATIONS")
@Schema(name = "Операции")
public class OperationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Идентификатор для Account")
    @ManyToOne
    @JoinColumn(name = "ID_ACCOUNT", foreignKey = @ForeignKey(name = "FK_1"))
    private AccountEntity idAccount;

    @Schema(description = "Идентификатор для TypeOperation")
    @ManyToOne
    @JoinColumn(name = "ID_TYPE_OPERATION", foreignKey = @ForeignKey(name = "FK_2"))
    private TypeOperationEntity typeOperationEntity;

    @Column(name = "SUM")
    @Schema(description = "Сумма")
    private BigDecimal sum;

    @Column(name = "DATA")
    @Schema(description = "Дата")
    private long dataTimeStamp;

    public OperationsEntity() {
    }
}
