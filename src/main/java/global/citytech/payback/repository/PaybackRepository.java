package global.citytech.payback.repository;

import global.citytech.borrow.repository.Borrow;
import global.citytech.platform.common.enums.PaybackStatus;
import global.citytech.user.repository.User;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface PaybackRepository extends CrudRepository<Payback, UUID> {

    List<Payback> findByBorrowerOrLenderAndPaybackStatusIn(String borrower, String lender, PaybackStatus paybackStatus);
    List<Payback> findByPaybackStatus(PaybackStatus paybackStatus);
}
