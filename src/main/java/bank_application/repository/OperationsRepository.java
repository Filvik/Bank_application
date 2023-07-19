package bank_application.repository;

import bank_application.model.AccountEntity;
import bank_application.model.OperationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OperationsRepository extends JpaRepository<OperationsEntity, Long> {
    List<OperationsEntity> findAllByIdAccountAndDataTimeStampBetweenOrderByDataTimeStamp(AccountEntity idAccount, Long dataStart , Long dataStop);
}
