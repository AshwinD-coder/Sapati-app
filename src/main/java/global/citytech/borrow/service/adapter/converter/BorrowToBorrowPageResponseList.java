package global.citytech.borrow.service.adapter.converter;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.service.borrowpage.BorrowPageResponse;

import java.util.ArrayList;
import java.util.List;

public class BorrowToBorrowPageResponseList {
    private BorrowToBorrowPageResponseList(){}


    public static List<BorrowPageResponse> toBorrowPageResponseList(List<Borrow> borrowList){
        List<BorrowPageResponse> borrowPageResponseList = new ArrayList<>();
        for (Borrow borrow:borrowList
             ) {
            BorrowPageResponse borrowPageResponse = new BorrowPageResponse();
            borrowPageResponse.setTransactionId(borrow.getTransactionId());
            borrowPageResponse.setRequestFrom(borrow.getBorrower());
            borrowPageResponse.setRequestTo(borrow.getLender());
            borrowPageResponse.setAmount(borrow.getAmount());
            borrowPageResponse.setInterestRate(borrow.getInterestRate());
            borrowPageResponse.setRemarks(borrow.getRemarks());
            borrowPageResponse.setRequestedAt(borrow.getRequestedAt().toString());
            borrowPageResponse.setReturnDate(borrow.getReturnDate().toString());
            borrowPageResponse.setRequestStatus(borrow.getRequestStatus());
            borrowPageResponseList.add(borrowPageResponse);
        }
        return borrowPageResponseList;

    }
}
