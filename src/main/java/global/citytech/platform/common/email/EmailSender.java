package global.citytech.platform.common.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private EmailSender() {
    }

    public static void sendMail(EmailConfiguration emailConfiguration) {
        try {
            MimeMessage mimeMessage = new MimeMessage(getSession());
            mimeMessage.setFrom(emailConfiguration.getFromEmail());
            mimeMessage.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(emailConfiguration.getToEmail())});
            mimeMessage.setSubject(emailConfiguration.getSubject());
            mimeMessage.setContent(emailConfiguration.getHtmlContent(), "text/html");
            Transport.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static Session getSession() {

        //Creating session
        return Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("appsapati@gmail.com", "qhkc neyh xkar zhyd");
            }
        });
    }

    private static Properties getProperties() {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        //setting host properties
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }
}


