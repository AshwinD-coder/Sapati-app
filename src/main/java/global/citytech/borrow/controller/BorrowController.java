package global.citytech.borrow.controller;

import global.citytech.borrow.service.acceptreject.AcceptRejectRequest;
import global.citytech.borrow.service.acceptreject.AcceptRejectResponse;
import global.citytech.borrow.service.acceptreject.AcceptRejectService;
import global.citytech.borrow.service.adapter.dto.BorrowDto;
import global.citytech.borrow.service.borrow.BorrowService;
import global.citytech.borrow.service.borrowpage.BorrowPageResponse;
import global.citytech.borrow.service.borrowpage.BorrowPageService;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.service.qr.UserQrRequest;
import global.citytech.user.service.qr.UserQrService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
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

    @Post("/")
    public HttpResponse<CustomResponseHandler<String>> borrowMoney(@Body BorrowDto borrowDto) throws ParseException {
        try {
            return HttpResponse.ok().body(borrowService.borrowMoney(borrowDto));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }

    @Post(value = "/qr", consumes = MediaType.MULTIPART_FORM_DATA)
    public HttpResponse<CustomResponseHandler<String>> getImage(
            CompletedFileUpload lender, @Part(value = "amount") Integer amount,
            @Part(value = "interestRate") Double interestRate,
             @Part("returnDate") String returnDate, @Part("remarks") String remarks) {
        try {
            UserQrRequest userQrRequest = UserQrService.readQR(lender.getFilename());
            BorrowDto borrowDto = new BorrowDto( userQrRequest.getUsername(), amount,interestRate ,remarks, returnDate);
            return HttpResponse.ok().body(borrowService.borrowMoney(borrowDto));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.badRequest(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }


    @Get("/pending")
    public HttpResponse<CustomResponseHandler<List<BorrowPageResponse>>> viewPendingPage() {
        try {
            return HttpResponse.ok().body(borrowPageService.viewPendingPage());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.notFound(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }

    @Get("/accepted")
    public HttpResponse<CustomResponseHandler<List<BorrowPageResponse>>> viewAcceptedPage() {
        try {
            return HttpResponse.ok().body(borrowPageService.viewAcceptedPage());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.notFound(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }

    @Get("/rejected")
    public HttpResponse<CustomResponseHandler<List<BorrowPageResponse>>> viewRejectedPage() {
        try {
            return HttpResponse.ok().body(borrowPageService.viewRejectedPage());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.notFound(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }

    @Get("/expired")
    public HttpResponse<CustomResponseHandler<List<BorrowPageResponse>>> viewExpiredPage() {
        try {
            return HttpResponse.ok().body(borrowPageService.viewExpiredPage());
        } catch (Exception e) {
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0", e.getMessage(), null));
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
    public HttpResponse<CustomResponseHandler<AcceptRejectResponse>> rejectRequest(@Body AcceptRejectRequest acceptRejectRequest) {
        try {
            return HttpResponse.ok().body(acceptRejectService.rejectRequest(acceptRejectRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.badRequest().body(new CustomResponseHandler<>("0", e.getMessage(), null));
        }
    }

}
