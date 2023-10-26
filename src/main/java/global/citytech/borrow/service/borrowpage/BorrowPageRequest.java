package global.citytech.borrow.service.borrowpage;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class BorrowPageRequest {
    // Empty because Borrow Page list doesn't require any body request as JWT is implemented.
}
