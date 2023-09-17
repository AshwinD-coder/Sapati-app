package global.citytech.borrow.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/borrow")
public class MoneyRequestController {

    @Post("/")
    public String borrowMoney(){
        return "Money Successfully requested";
    }

}
