package global.citytech.moneyrequest.controller;

import global.citytech.moneyrequest.service.acceptreject.AcceptRejectRequest;
import global.citytech.moneyrequest.service.acceptreject.AcceptRejectService;
import global.citytech.moneyrequest.service.adapter.dto.MoneyRequestDto;
import global.citytech.moneyrequest.service.moneyrequest.MoneyRequestService;
import global.citytech.moneyrequest.service.requestpage.RequestPageEntry;
import global.citytech.moneyrequest.service.requestpage.RequestPageService;
import global.citytech.platform.CustomResponseHandler;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.text.ParseException;

@Controller("/money-request")
public class MoneyRequestController {
    @Inject
    private MoneyRequestService moneyRequestService;

    @Inject
    private RequestPageService requestPageService;

    @Inject
    private AcceptRejectService acceptRejectService;

    @Get("/")
    public String index(){
        return "Money Request Page!";
    }

    @Post("/borrow")
    public  HttpResponse<?> borrowMoney(@Body MoneyRequestDto moneyRequestDto) throws ParseException {
        try{
            return HttpResponse.ok().body(moneyRequestService.requestMoney(moneyRequestDto));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("400",e.getMessage(),null));
        }
    }

    @Post("/pending")
    public HttpResponse<String> viewPendingPage(@Body RequestPageEntry requestPageEntry){
        return  HttpResponse.status(200,"Found").body(requestPageService.viewPendingPage(requestPageEntry).toString()).contentType(MediaType.APPLICATION_JSON_TYPE);
    }
    @Post("/accepted")
    public HttpResponse<String> viewAcceptedPage(@Body RequestPageEntry requestPageEntry){
        return  HttpResponse.status(200,"Found").body(requestPageService.viewAcceptedPage(requestPageEntry).toString()).contentType(MediaType.APPLICATION_JSON_TYPE);
    }
    @Post("/rejected")
    public HttpResponse<String> viewRejectedPage(@Body RequestPageEntry requestPageEntry){
        return  HttpResponse.status(200,"Found").body(requestPageService.viewRejectedPage(requestPageEntry).toString()).contentType(MediaType.APPLICATION_JSON_TYPE);
    }
    @Post("/expired")
    public HttpResponse<String> viewExpiredPage(@Body RequestPageEntry requestPageEntry){
        return  HttpResponse.status(200,"Found").body(requestPageService.viewExpiredPage(requestPageEntry).toString()).contentType(MediaType.APPLICATION_JSON_TYPE);
    }

    @Post("/accept")
    public HttpResponse<?> acceptRequest(@Body AcceptRejectRequest acceptRejectRequest) {
        try {
            return HttpResponse.ok().body(acceptRejectService.acceptRequest(acceptRejectRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("400", e.getMessage(), null));
        }
    }
    @Post("/reject")
    public HttpResponse<?> rejectRequest(@Body AcceptRejectRequest acceptRejectRequest){
        try{
            return HttpResponse.ok().body(acceptRejectService.rejectRequest(acceptRejectRequest));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("400",e.getMessage(),null));
        }
    }

}
