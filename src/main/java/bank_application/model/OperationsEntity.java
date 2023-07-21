package bank_application.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "OPERATIONS")
@Schema(name = "Операции")
@NoArgsConstructor
public class OperationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Идентификатор для Account")
    @ManyToOne
    @JoinColumn(name = "ID_ACCOUNT", foreignKey = @ForeignKey(name = "FK_1"), nullable = false)
    private AccountEntity idAccount;

    @Schema(description = "Идентификатор для TypeOperation")
    @ManyToOne
    @JoinColumn(name = "ID_TYPE_OPERATION", foreignKey = @ForeignKey(name = "FK_2"), nullable = false)
    private TypeOperationEntity typeOperationEntity;

    @Column(name = "SUM", nullable = false)
    @Schema(description = "Сумма")
    private BigDecimal sum;

    @Column(name = "DATA", nullable = false)
    @Schema(description = "Дата")
    private long dataTimeStamp;

    @Schema(description = "Идентификатор для AccountRecipient")
    @ManyToOne
        @JoinColumn(name = "ID_ACCOUNT_RECIPIENT", foreignKey = @ForeignKey(name = "FK_3"))
    private AccountEntity idAccountRecipient;

    public OperationsEntity(AccountEntity idAccount, AccountEntity idRecipient, TypeOperationEntity typeOperationEntity, BigDecimal sum, long dataTimeStamp) {
        this.idAccount = idAccount;
        this.typeOperationEntity = typeOperationEntity;
        this.sum = sum;
        this.dataTimeStamp = dataTimeStamp;
        this.idAccountRecipient = idRecipient;
    }
    public OperationsEntity(AccountEntity idAccount, TypeOperationEntity typeOperationEntity, BigDecimal sum, long dataTimeStamp) {
        this.idAccount = idAccount;
        this.typeOperationEntity = typeOperationEntity;
        this.sum = sum;
        this.dataTimeStamp = dataTimeStamp;
    }
}
