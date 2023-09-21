package global.citytech.payback.repository;

import global.citytech.platform.common.enums.PaybackStatus;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PaybackRepository extends CrudRepository<Payback, UUID> {
    List<Payback> findByBorrowerOrLenderAndPaybackStatusIn(String borrower, String lender, PaybackStatus paybackStatus);
}
