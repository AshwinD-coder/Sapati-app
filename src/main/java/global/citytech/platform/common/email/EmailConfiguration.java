package global.citytech.platform.common.email;

public class EmailConfiguration {
    private final String fromEmail = "appsapati@gmail.com";
    private String toEmail;
    private String subject;
    private String htmlContent;

    public EmailConfiguration(String toEmail, String subject,String htmlContent) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.htmlContent = htmlContent;
    }

    public EmailConfiguration() {

    }

    public String getFromEmail() {
        return fromEmail;
    }


    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
