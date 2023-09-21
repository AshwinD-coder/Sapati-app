package global.citytech.payback.controller;

import global.citytech.payback.repository.Payback;
import global.citytech.payback.service.accept.PaybackAcceptRequest;
import global.citytech.payback.service.accept.PaybackAcceptResponse;
import global.citytech.payback.service.accept.PaybackAcceptService;
import global.citytech.payback.service.paybackpage.PaybackPageRequest;
import global.citytech.payback.service.paybackpage.PaybackPageResponse;
import global.citytech.payback.service.paybackpage.PaybackPageService;
import global.citytech.platform.common.response.CustomResponseHandler;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/payback")
public class PaybackController {
    @Inject
    PaybackAcceptService paybackAcceptService;

    @Inject
    PaybackPageService paybackPageService;
    @Get("/")
    public String index(){
        return "Payback Page!";
    }
    @Post("/accept")
    public HttpResponse<CustomResponseHandler<PaybackAcceptResponse>> acceptPayback(@Body PaybackAcceptRequest paybackAcceptRequest){
        try{
            return HttpResponse.ok().body(paybackAcceptService.acceptPayback(paybackAcceptRequest));
        }
        catch (Exception e){
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0",e.getMessage(),null));
        }

    }

    @Post("/paid-page")
    public HttpResponse<CustomResponseHandler<List<PaybackPageResponse>>> viewPaidPaybackPage(@Body PaybackPageRequest paybackPageRequest){
        try{
            return HttpResponse.ok().body(paybackPageService.viewPaidPaybackPage(paybackPageRequest));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0",e.getMessage(),null));
        }
    }
}
