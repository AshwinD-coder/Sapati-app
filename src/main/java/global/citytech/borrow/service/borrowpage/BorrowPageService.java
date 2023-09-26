package global.citytech.borrow.service.borrowpage;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.borrow.service.adapter.converter.BorrowToBorrowPageResponseList;
import global.citytech.platform.common.enums.RequestStatus;
import global.citytech.platform.common.response.CustomResponseHandler;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;


public class BorrowPageService {
    @Inject
    private BorrowRepository borrowRepository;

    @Inject
    private UserRepository userRepository;

    public BorrowPageService(BorrowRepository borrowRepository, UserRepository userRepository) {
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
    }

    public void validateBorrowPageRequest(BorrowPageRequest borrowPageRequest){
        Optional<User> user = this.userRepository.findByUsername(borrowPageRequest.getUsername());
        if(user.isEmpty()){
            throw  new IllegalArgumentException("User not found!");
        }
    }

    public CustomResponseHandler<List<BorrowPageResponse>> viewPendingPage(BorrowPageRequest borrowPageRequest) {
        validateBorrowPageRequest(borrowPageRequest);
        List<Borrow> pendingRequestList = this.borrowRepository.findByBorrowerOrLenderAndRequestStatusIn(borrowPageRequest.getUsername(), borrowPageRequest.getUsername(), RequestStatus.PENDING);
        if (pendingRequestList.isEmpty()) {
            throw new IllegalArgumentException("Pending List Empty!");
        }
        List<BorrowPageResponse> pendingPageResponseList = BorrowToBorrowPageResponseList.toBorrowPageResponseList(pendingRequestList);
        return new CustomResponseHandler<>("0", "Pending page retrieved!", pendingPageResponseList);


    }

    public CustomResponseHandler<List<BorrowPageResponse>> viewAcceptedPage(BorrowPageRequest borrowPageRequest) {
        validateBorrowPageRequest(borrowPageRequest);
        List<Borrow> acceptedRequestList = this.borrowRepository.findByBorrowerOrLenderAndRequestStatusIn(borrowPageRequest.getUsername(), borrowPageRequest.getUsername(), RequestStatus.ACCEPTED);
        if (acceptedRequestList.isEmpty()) {
            throw new IllegalArgumentException("Accepted List Empty!");
        }
        List<BorrowPageResponse> acceptedPageResponseList =  BorrowToBorrowPageResponseList.toBorrowPageResponseList(acceptedRequestList);
        return new CustomResponseHandler<>("0", "Accepted page retrieved!",acceptedPageResponseList);
    }

    public CustomResponseHandler<List<BorrowPageResponse>> viewRejectedPage(BorrowPageRequest borrowPageRequest) {
        validateBorrowPageRequest(borrowPageRequest);
        List<Borrow> rejectedRequestList = this.borrowRepository.findByBorrowerOrLenderAndRequestStatusIn(borrowPageRequest.getUsername(), borrowPageRequest.getUsername(), RequestStatus.REJECTED);
        if (rejectedRequestList.isEmpty()) {
            throw new IllegalArgumentException("Rejected List Empty!");
        }
        List<BorrowPageResponse> rejectedPageResponseList = BorrowToBorrowPageResponseList.toBorrowPageResponseList(rejectedRequestList);
        return new CustomResponseHandler<>("0", "Rejected page retrieved!", rejectedPageResponseList);

    }

    public CustomResponseHandler<List<BorrowPageResponse>> viewExpiredPage(BorrowPageRequest borrowPageRequest) {
        validateBorrowPageRequest(borrowPageRequest);
      List<Borrow> expiredRequestList = this.borrowRepository.findByBorrowerOrLenderAndRequestStatusIn(borrowPageRequest.getUsername(),borrowPageRequest.getUsername(),RequestStatus.EXPIRED);
      if(expiredRequestList.isEmpty()){
          throw new IllegalArgumentException("Expired List Empty");
      }
      List<BorrowPageResponse> expiredPageResponseList = BorrowToBorrowPageResponseList.toBorrowPageResponseList(expiredRequestList);
      return new CustomResponseHandler<>("0","Expired page retrieved!",expiredPageResponseList);
    }
}