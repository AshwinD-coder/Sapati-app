package global.citytech.borrow.controller;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.service.acceptreject.AcceptRejectRequest;
import global.citytech.borrow.service.acceptreject.AcceptRejectResponse;
import global.citytech.borrow.service.acceptreject.AcceptRejectService;
import global.citytech.borrow.service.adapter.dto.BorrowDto;
import global.citytech.borrow.service.borrow.BorrowService;
import global.citytech.borrow.service.borrowpage.BorrowPageRequest;
import global.citytech.borrow.service.borrowpage.BorrowPageResponse;
import global.citytech.borrow.service.borrowpage.BorrowPageService;
import global.citytech.platform.common.response.CustomResponseHandler;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.text.ParseException;
import java.util.List;

@Controller("/money-request")
public class BorrowController {
    @Inject
    private BorrowService borrowService;

    @Inject
    private BorrowPageService borrowPageService;

    @Inject
    private AcceptRejectService acceptRejectService;

    @Get("/")
    public String index(){
        return "Money Request Page!";
    }

    @Post("/")
    public  HttpResponse<CustomResponseHandler<String>> borrowMoney(@Body BorrowDto borrowDto) throws ParseException {
        try{
            return HttpResponse.ok().body(borrowService.requestMoney(borrowDto));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0",e.getMessage(),null));
        }
    }

    @Post("/pending")
    public HttpResponse<CustomResponseHandler<List<BorrowPageResponse>>> viewPendingPage(@Body BorrowPageRequest borrowPageRequest){
        try{
            return HttpResponse.ok().body(borrowPageService.viewPendingPage(borrowPageRequest));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.notFound(new CustomResponseHandler<>("0",e.getMessage(),null));
        }
    }
    @Post("/accepted")
    public HttpResponse<CustomResponseHandler<List<BorrowPageResponse>>> viewAcceptedPage(@Body BorrowPageRequest borrowPageRequest){
        try{
            return HttpResponse.ok().body(borrowPageService.viewAcceptedPage(borrowPageRequest));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.notFound(new CustomResponseHandler<>("0",e.getMessage(),null));
        }
    }
    @Post("/rejected")
    public HttpResponse<CustomResponseHandler<List<BorrowPageResponse>>> viewRejectedPage(@Body BorrowPageRequest borrowPageRequest){
        try{
            return HttpResponse.ok().body(borrowPageService.viewRejectedPage(borrowPageRequest));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.notFound(new CustomResponseHandler<>("0",e.getMessage(),null));
        }
    }
    @Post("/expired")
    public HttpResponse<CustomResponseHandler<List<BorrowPageResponse>>> viewExpiredPage(@Body BorrowPageRequest borrowPageRequest){
        try{
            return HttpResponse.ok().body(borrowPageService.viewExpiredPage(borrowPageRequest));
        }
        catch (Exception e){
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0",e.getMessage(),null));
        }
    }

    @Post("/accept")
    public HttpResponse<CustomResponseHandler<AcceptRejectResponse>> acceptRequest(@Body AcceptRejectRequest acceptRejectRequest) {
        try {
            return HttpResponse.ok().body(acceptRejectService.acceptRequest(acceptRejectRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }
    @Post("/reject")
    public HttpResponse<CustomResponseHandler<AcceptRejectResponse>> rejectRequest(@Body AcceptRejectRequest acceptRejectRequest){
        try{
            return HttpResponse.ok().body(acceptRejectService.rejectRequest(acceptRejectRequest));
        }
        catch (Exception e){
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0",e.getMessage(),null));
        }
    }

}
