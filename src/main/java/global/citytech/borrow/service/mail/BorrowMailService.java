package global.citytech.borrow.service.mail;

import global.citytech.borrow.repository.Borrow;
import global.citytech.borrow.repository.BorrowRepository;
import global.citytech.platform.common.email.EmailConfiguration;
import global.citytech.user.repository.User;
import global.citytech.user.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

public class BorrowMailService {
    @Inject
    BorrowRepository borrowRepository;
    @Inject
    UserRepository userRepository;
    private String htmlContent;


    public BorrowMailService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public EmailConfiguration setEmailConfigurationOfBorrowMailForLender(Borrow borrow) {
        EmailConfiguration emailConfiguration = new EmailConfiguration();
        Optional<User> lender = this.userRepository.findByUsername(borrow.getLender());
        if (lender.isPresent()) {
            emailConfiguration.setSubject("Money Request Details");
            emailConfiguration.setToEmail(lender.get().getEmail());
            htmlContent = " <html>Dear <b>" + lender.get().getUsername() +
                    "</b>" +
                    """
                    <br><br><br>
                                        
                    I hope this email finds you well. I am writing to provide you with the details of a recent money request.<br><br>
                                        
                    You are requested an amount of <b>""" + borrow.getAmount() +
                    "</b> with an interest rate of <b>" + borrow.getInterestRate() +
                    "</b> and a return date of <b>" + borrow.getReturnDate() + "</b>.<br><br>" +
                    """
                            The borrower for this request is <b>
                            """
                    + borrow.getBorrower() +
                    "</b>   and the status of your request is <b>" + borrow.getRequestStatus() + "</b>.Note:: <b>This request will expire in 2 days if you do not accept or reject the request.</b><br><br>" +
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

        } else throw new IllegalArgumentException("Cannot find lender to send borrow mail!");

    }

    public EmailConfiguration setEmailConfigurationOfBorrowMailForBorrower(Borrow borrow) {
        EmailConfiguration emailConfiguration = new EmailConfiguration();
        Optional<User> borrower = this.userRepository.findByUsername(borrow.getBorrower());
        if (borrower.isPresent()) {
            emailConfiguration.setToEmail(borrower.get().getEmail());
            emailConfiguration.setSubject("Money Request Details");
            htmlContent = """
                    <html>
                    Dear <b>""" + borrower.get().getUsername() + "</b>," + """
                    <br><br><br>
                    I hope this email finds you well. I am writing to provide you with the details of a recent money request.<br><br>
                    You requested an amount of <b>""" + borrow.getAmount() +
                    "</b> with an interest rate of <b>" + borrow.getInterestRate() +
                    "</b> and a return date of <b>" + borrow.getReturnDate() + "</b>.<br><br>" +
                    "The lender for this request is <b>"
                    + borrow.getLender() +
                    "</b>   and the status of your request is <b>" + borrow.getRequestStatus() + "</b> Please wait for the lender to accept the request.Note:: <b>This request will expire in 2 days if lender will not accept or reject the request.</b><br><br>" +
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

        } else throw new IllegalArgumentException("Cannot find borrower to send borrow mail!");

    }

    public EmailConfiguration setEmailConfigurationOfAcceptedBorrowMailForBorrower(Borrow borrow) {
        EmailConfiguration emailConfiguration = new EmailConfiguration();
        Optional<User> borrower = this.userRepository.findByUsername(borrow.getBorrower());
        if (borrower.isPresent()) {
            emailConfiguration.setToEmail(borrower.get().getEmail());
            emailConfiguration.setSubject("Money Request Accepted");
            htmlContent = """
                    <html>
                    Dear <b>""" + borrower.get().getUsername() + "</b>," + """
                    <br><br><br>
                    I hope this email finds you well. I am writing to provide you with the details of a recent money request.
                    The money request you sent to\s"""+borrow.getLender()+
                    """ 
                     has been <b>ACCEPTED</b>.The fund you requested has been transferred to your account.
                    <br><br>
                    <b> Money Request Details </b>
                    <table style = 'border:solid,1px;'>
                    <tr>
                    <th>Amount</th>
                    <td>"""
                    +borrow.getAmount()+
                    """
                    </td>
                    </tr>
                    <tr>
                    <th>Interest Rate</th>
                    <td>"""
                    +borrow.getInterestRate()+
                    """
                    </td>
                    </tr>
                    <tr>
                    <th>Payback Deadline</th>
                    <td>"""
                    +borrow.getReturnDate()+
                    """
                    </td>
                    </tr>
                    </table><br>
                    The lender for this request is <b>
                    """
                    + borrow.getLender() +
                    "</b>   and the status of your request is <b>" + borrow.getRequestStatus() + "</b>Please return the money before the dealine or you will be blacklisted!<br>" +
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

        } else throw new IllegalArgumentException("Cannot find borrower to send borrow mail!");

    }
    public EmailConfiguration setEmailConfigurationOfRejectedBorrowMailForBorrower(Borrow borrow) {
        EmailConfiguration emailConfiguration = new EmailConfiguration();
        Optional<User> borrower = this.userRepository.findByUsername(borrow.getBorrower());
        if (borrower.isPresent()) {
            emailConfiguration.setToEmail(borrower.get().getEmail());
            emailConfiguration.setSubject("Money Request Rejected");
            htmlContent = """
                    <html>
                    Dear <b>""" + borrower.get().getUsername() + "</b>," + """
                    <br><br><br>
                    I hope this email finds you well. I am writing to provide you with the details of a recent money request.
                    The money request you sent to\s<b>"""+borrow.getLender()+"</b> "+
                    """ 
                     has been <b>REJECTED</b>.<br><br>
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

        } else throw new IllegalArgumentException("Cannot find borrower to send borrow mail!");

    }

}
