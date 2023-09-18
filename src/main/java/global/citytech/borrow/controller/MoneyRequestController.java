package global.citytech.borrow.controller;

import global.citytech.borrow.dto.MoneyRequestDto;
import global.citytech.borrow.service.moneyrequest.MoneyRequestService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.text.ParseException;

@Controller("/borrow")
public class MoneyRequestController {
    @Inject
    MoneyRequestService moneyRequestService;

    @Get("/")
    public String index(){
        return "Money Request Page!";
    }

    @Post("/money")
    public  String borrowMoney(@Body MoneyRequestDto moneyRequestDto) throws ParseException {
        return moneyRequestService.requestMoney(moneyRequestDto);
    }

}
