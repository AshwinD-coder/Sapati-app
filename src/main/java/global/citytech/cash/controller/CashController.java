package global.citytech.cash.controller;

import global.citytech.cash.service.adapter.dto.CashDepositDto;
import global.citytech.cash.service.balance.CashBalanceRequest;
import global.citytech.cash.service.balance.CashBalanceResponse;
import global.citytech.cash.service.balance.CashBalanceService;
import global.citytech.cash.service.deposit.CashDepositService;
import global.citytech.platform.common.response.CustomResponseHandler;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/cash")
public class CashController {
    @Inject
    CashDepositService cashDepositService;
    @Inject
    CashBalanceService cashBalanceService;

    @Post("/deposit")
    public HttpResponse<CustomResponseHandler<CashDepositDto>> depositAmount(@Body CashDepositDto cashDepositDto) {
        try{
            return HttpResponse.ok().body(cashDepositService.depositAmount(cashDepositDto));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0",e.getMessage(),null));
        }

    }
    @Post("/balance")
    public HttpResponse<CustomResponseHandler<CashBalanceResponse>> getBalance(@Body CashBalanceRequest cashBalanceRequest){
        try{
            return  HttpResponse.ok().body(cashBalanceService.getCashBalance(cashBalanceRequest));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest(new CustomResponseHandler<>("0",e.getMessage(),null));
        }
    }
}
