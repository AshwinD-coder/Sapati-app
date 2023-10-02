package global.citytech.platform.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    private EmailService(){}
    public static void sendMail(EmailConfiguration emailConfiguration){
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        //setting host properties
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        //Creating session

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("appsapati@gmail.com","qhkc neyh xkar zhyd");
            }
        });
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        try{
            mimeMessage.setFrom(emailConfiguration.getFromEmail());
            mimeMessage.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(emailConfiguration.getToEmail())});
            mimeMessage.setSubject(emailConfiguration.getSubject());
            mimeMessage.setContent(emailConfiguration.getHtmlContent(),"text/html");
            Transport.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}


