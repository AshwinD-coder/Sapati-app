package global.citytech.cash.controller;

import global.citytech.cash.service.adapter.dto.CashDto;
import global.citytech.cash.service.deposit.CashDepositService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/cash")
public class CashController {
    @Inject
    CashDepositService cashDepositService;

    @Post("/deposit")
    public HttpResponse<String> depositAmount(@Body CashDto cashDto) {
        return HttpResponse.status(200, "Deposited").body(cashDepositService.depositAmount(cashDto));


    }
}
