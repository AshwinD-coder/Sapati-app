package global.citytech.user.repository;


import global.citytech.user.model.User;
import global.citytech.user.model.UserType;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUserType(UserType userType);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailIn(String email);



}
