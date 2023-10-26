package global.citytech.payback.service.interest;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class InterestService {
    @Inject
    BorrowRepository borrowRepository;

    @Inject
    private PaybackRepository paybackRepository;

    public InterestService(BorrowRepository borrowRepository, PaybackRepository paybackRepository) {
        this.borrowRepository = borrowRepository;
        this.paybackRepository = paybackRepository;
    }

    public InterestService() {

    }

    public  Double calculateInterestForPaybackAccept(UUID paybackId){
        Optional<Payback> payback = this.paybackRepository.findById(paybackId);
        Optional<Borrow> borrow = this.borrowRepository.findById(paybackId);
        if(payback.isEmpty() || borrow.isEmpty()){
            throw new IllegalArgumentException("Payback Id not Found!");
        }
        Integer principal = payback.get().getPaybackAmount();
        Double interestRate = payback.get().getInterestRate();
        Date acceptedDate = borrow.get().getRequestStatusUpdatedOn();
        Date paybackDate = new Date();
        long totalMillisecondsOfMoneyBorrow = paybackDate.getTime() - acceptedDate.getTime();
        double days = ((double)totalMillisecondsOfMoneyBorrow/(1000*60*60*24));
        return (principal * interestRate * days)/(100*30);
    }

}
