package global.citytech.borrow.service.mail;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.platform.email.EmailConfiguration;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class BorrowMailService {
    @Inject
    BorrowRepository borrowRepository;
    @Inject
    UserRepository userRepository;

    public BorrowMailService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }
    private String htmlContent ;

    public EmailConfiguration setEmailConfigurationForBorrowMailLender(Borrow borrow){
        EmailConfiguration emailConfiguration = new EmailConfiguration();
        Optional<User> lender = this.userRepository.findByUsername(borrow.getLender());
        if(lender.isPresent()) {
            emailConfiguration.setToEmail(lender.get().getEmail());
            htmlContent = """
                    <html>
                    Dear <b>"""+lender.get().getUsername()+"</b>,"+"""
                    <br><br><br>
                                        
                    I hope this email finds you well. I am writing to provide you with the details of a recent money request.<br><br>
                                        
                    You are requested an amount of <b>"""+borrow.getAmount()+
                    """
                    </b> with an interest rate of <b>"""+ borrow.getInterestRate()+
                    """
                    </b> and a return date of <b>"""+borrow.getReturnDate()+ "</b>.<br><br>" +
                    """
                    The borrower for this request is <b>
                    """
                    +borrow.getBorrower()+
                    """ 
                         </b>   and the status of your request is <b>""" +borrow.getRequestStatus()+"</b>.<br><br>"+
                    """
                                        
                    If you have any further questions or concerns regarding your request, please do not hesitate to contact us. We are here to help you with any financial needs you may have.
                                        
                    Thank you for choosing our services and we look forward to working with you again in the future.
                         
                         
                    <br><br>                    
                    Best regards,
                                        
                    Sapati App
                    </html>
                    """;
            emailConfiguration.setHtmlContent(htmlContent);
            return emailConfiguration;

        }
        else throw new IllegalArgumentException("Cannot find lender to send borrow mail!");

    }
}
