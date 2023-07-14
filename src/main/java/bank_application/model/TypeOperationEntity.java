package bank_application.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TYPE_OPERATION")
@Schema(name = "Тип операции")
public class TypeOperationEntity {

    @Id
    @Column(name = "ID")
    @Schema(description = "Идентификатор операции")
    private int id;

    @Column(name = "DESCRIPTION")
    @Schema(description = "Название операции")
    private String description;

    public TypeOperationEntity() {
    }
}
