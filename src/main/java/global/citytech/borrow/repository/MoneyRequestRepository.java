package global.citytech.borrow.repository;

import global.citytech.borrow.model.MoneyRequest;
import global.citytech.user.model.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface MoneyRequestRepository extends CrudRepository<MoneyRequest, UUID> {
    Optional<MoneyRequest> findByRequestFrom (String requestFrom);
    Optional<MoneyRequest> findByRequestTo (String requestTo);
}
