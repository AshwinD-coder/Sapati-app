package global.citytech.moneyrequest.controller;

import global.citytech.moneyrequest.service.adapter.dto.MoneyRequestDto;
import global.citytech.moneyrequest.service.moneyrequest.MoneyRequestService;
import global.citytech.moneyrequest.service.requestpage.RequestPageEntry;
import global.citytech.moneyrequest.service.requestpage.RequestPageService;
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

    @Get("/")
    public String index(){
        return "Money Request Page!";
    }

    @Post("/borrow")
    public  String borrowMoney(@Body MoneyRequestDto moneyRequestDto) throws ParseException {
        return moneyRequestService.requestMoney(moneyRequestDto);
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
}
