package global.citytech.moneyrequest.service.requestpage;

import global.citytech.moneyrequest.repository.MoneyRequest;
import global.citytech.moneyrequest.repository.RequestStatus;
import global.citytech.moneyrequest.repository.MoneyRequestRepository;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;


public class RequestPageService {
    @Inject
    private MoneyRequestRepository moneyRequestRepository;

    public RequestPageService(MoneyRequestRepository moneyRequestRepository) {
        this.moneyRequestRepository = moneyRequestRepository;
    }

    public List<MoneyRequest> viewPendingPage(RequestPageEntry requestPageEntry) {
        if (!this.moneyRequestRepository.findByBorrowerOrLenderAndRequestStatusIn(requestPageEntry.getUsername(),requestPageEntry.getUsername(), RequestStatus.PENDING).isEmpty()){
            return new ArrayList<>(this.moneyRequestRepository.findByBorrowerOrLenderAndRequestStatusIn(requestPageEntry.getUsername(),requestPageEntry.getUsername(), RequestStatus.PENDING));
        } else {
            throw new IllegalArgumentException("No Pending List!");
        }
    }

    public List<MoneyRequest> viewAcceptedPage(RequestPageEntry requestPageEntry) {
        if (!this.moneyRequestRepository.findByBorrowerOrLenderAndRequestStatusIn(requestPageEntry.getUsername(),requestPageEntry.getUsername(), RequestStatus.ACCEPTED).isEmpty()){
            return new ArrayList<>(this.moneyRequestRepository.findByBorrowerOrLenderAndRequestStatusIn(requestPageEntry.getUsername(),requestPageEntry.getUsername(), RequestStatus.ACCEPTED));
        } else {
            throw new IllegalArgumentException("No Accepted List!");
        }
    }
    public List<MoneyRequest> viewRejectedPage(RequestPageEntry requestPageEntry) {
        if (!this.moneyRequestRepository.findByBorrowerOrLenderAndRequestStatusIn(requestPageEntry.getUsername(),requestPageEntry.getUsername(), RequestStatus.REJECTED).isEmpty()){
            return new ArrayList<>(this.moneyRequestRepository.findByBorrowerOrLenderAndRequestStatusIn(requestPageEntry.getUsername(),requestPageEntry.getUsername(), RequestStatus.REJECTED));
        } else {
            throw new IllegalArgumentException("No Rejected List!");
        }
    }
    public List<MoneyRequest> viewExpiredPage(RequestPageEntry requestPageEntry) {
        if (!this.moneyRequestRepository.findByBorrowerOrLenderAndRequestStatusIn(requestPageEntry.getUsername(),requestPageEntry.getUsername(), RequestStatus.EXPIRED).isEmpty()){
            return new ArrayList<>(this.moneyRequestRepository.findByBorrowerOrLenderAndRequestStatusIn(requestPageEntry.getUsername(),requestPageEntry.getUsername(), RequestStatus.EXPIRED));
        } else {
            throw new IllegalArgumentException("No Expired List!");
        }
    }
}