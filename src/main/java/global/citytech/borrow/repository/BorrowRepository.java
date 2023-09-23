package global.citytech.borrow.repository;

import global.citytech.platform.common.enums.RequestStatus;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface BorrowRepository extends CrudRepository<Borrow, UUID> {
    Optional<Borrow> findByBorrowerAndLenderAndRequestStatus(String borrower, String lender, RequestStatus requestStatus);
    List<Borrow> findByBorrowerOrLenderAndRequestStatusIn(String borrower, String lender, RequestStatus requestStatus);
    Optional<Borrow> findByTransactionId(UUID transactionId);

    List<Borrow> findByRequestStatusIn(RequestStatus requestStatus);
}
