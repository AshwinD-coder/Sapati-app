package global.citytech.moneyrequest.service.acceptreject;

import global.citytech.moneyrequest.repository.MoneyRequest;
import global.citytech.moneyrequest.repository.MoneyRequestRepository;
import global.citytech.moneyrequest.repository.RequestStatus;
import global.citytech.platform.CustomResponseHandler;
import jakarta.inject.Inject;

import java.util.Optional;

public class AcceptRejectService {
    @Inject
    MoneyRequestRepository moneyRequestRepository;

    public AcceptRejectService(MoneyRequestRepository moneyRequestRepository) {
        this.moneyRequestRepository = moneyRequestRepository;
    }

    public void validateRequest(AcceptRejectRequest acceptRejectRequest){
        Optional<MoneyRequest> moneyRequest = this.moneyRequestRepository.findById(acceptRejectRequest.getTransactionId());
        if(moneyRequest.isEmpty()){
            throw new IllegalArgumentException("Invalid money request id!");
        }
        if(moneyRequest.get().getRequestStatus()!=RequestStatus.PENDING){
            throw new IllegalArgumentException("Money Request already accepted, rejected or expired!");
        }
    }
    public CustomResponseHandler<?> acceptRequest(AcceptRejectRequest acceptRejectRequest){
        validateRequest(acceptRejectRequest);
        Optional<MoneyRequest> moneyRequest = this.moneyRequestRepository.findById(acceptRejectRequest.getTransactionId());
        MoneyRequest request = moneyRequest.get();
        request.setRequestStatus(RequestStatus.ACCEPTED);
        try {
            this.moneyRequestRepository.update(request);
            AcceptRejectResponse acceptRejectResponse = new AcceptRejectResponse(request.getBorrower(), request.getAmount(), request.getRequestStatus());
            return new CustomResponseHandler<>("200","Money Request Accepted!", acceptRejectResponse);

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public CustomResponseHandler<?> rejectRequest(AcceptRejectRequest acceptRejectRequest){
        validateRequest(acceptRejectRequest);
        Optional<MoneyRequest> moneyRequest = this.moneyRequestRepository.findById(acceptRejectRequest.getTransactionId());
        MoneyRequest request = moneyRequest.get();
        request.setRequestStatus(RequestStatus.REJECTED);
        try {
            this.moneyRequestRepository.update(request);
            AcceptRejectResponse acceptRejectResponse = new AcceptRejectResponse(request.getBorrower(), request.getAmount(), request.getRequestStatus());
            return new CustomResponseHandler<>("200","Money Request Rejected!", acceptRejectResponse);

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
