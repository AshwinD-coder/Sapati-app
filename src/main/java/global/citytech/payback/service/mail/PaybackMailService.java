package global.citytech.payback.service.mail;

import global.citytech.payback.repository.Payback;
import global.citytech.payback.repository.PaybackRepository;
import global.citytech.platform.common.email.EmailConfiguration;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class PaybackMailService {
    @Inject
    PaybackRepository paybackRepository;

    @Inject
    UserRepository userRepository;
    private String htmlContent;

    public PaybackMailService(PaybackRepository paybackRepository, UserRepository userRepository) {
        this.paybackRepository = paybackRepository;
        this.userRepository = userRepository;
    }

    public EmailConfiguration setEmailConfigurationForDeadline(Payback payback){
        EmailConfiguration emailConfiguration = new EmailConfiguration();
        Optional<User> borrower = this.userRepository.findByUsername(payback.getBorrower());
        if(borrower.isPresent()) {
            emailConfiguration.setToEmail(borrower.get().getEmail());
            emailConfiguration.setSubject("Payback Deadline");
            htmlContent = """
                    <html>
                        Dear User,
                        <br><br>The payback status of your transaction id :\s<b> 
                        """ + payback.getId() + "</b> is unpaid.Please payback the amount of Rs.<b>" + payback.getPaybackAmount() + "<\b>" + " with an interest rate of <b>" + payback.getInterestRate() + "</b> " +
                    ".The dealine for payback is <b>" + payback.getPaybackDeadline() + "</b>." +
                    """
                            If you have any further questions or concerns regarding your request, please do not hesitate to contact us. We are here to help you with any financial needs you may have.
                                        Thank you for choosing our services and we look forward to working with you again in the future.
                                        <br><br>                    
                                        Best regards,
                                        <br>      
                                        Sapati App
                            </html>
                            """;
            emailConfiguration.setHtmlContent(htmlContent);
            return emailConfiguration;
        }
        else throw new IllegalArgumentException("Borrower not found to send email!");
    }

}
