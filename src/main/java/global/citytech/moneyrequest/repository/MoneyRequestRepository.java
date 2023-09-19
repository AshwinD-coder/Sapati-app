package global.citytech.moneyrequest.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface MoneyRequestRepository extends CrudRepository<MoneyRequest, UUID> {
    Optional<MoneyRequest> findByBorrowerAndLenderAndRequestStatus(String borrower, String lender, RequestStatus requestStatus);
    List<MoneyRequest> findByBorrowerOrLenderAndRequestStatusIn(String borrower, String lender, RequestStatus requestStatus);
}
