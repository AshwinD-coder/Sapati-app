package global.citytech.payback.controller;

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
    @Post("/")
    public HttpResponse<CustomResponseHandler<PaybackAcceptResponse>> acceptPayback(@Body PaybackAcceptRequest paybackAcceptRequest){
        try{
            return HttpResponse.ok().body(paybackAcceptService.acceptPayback(paybackAcceptRequest));
        }
        catch (Exception e){
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0",e.getMessage(),null));
        }

    }

    @Get("/paid")
    public HttpResponse<CustomResponseHandler<List<PaybackPageResponse>>> viewPaidPaybackPage(){
        try{
            return HttpResponse.ok().body(paybackPageService.viewPaidPaybackPage());
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0",e.getMessage(),null));
        }
    }
    @Get("/unpaid")
    public HttpResponse<CustomResponseHandler<List<PaybackPageResponse>>> viewUnpaidPaybackPage(){
        try{
            return HttpResponse.ok().body(paybackPageService.viewUnpaidPaybackPage());
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0",e.getMessage(),null));
        }
    }
}
