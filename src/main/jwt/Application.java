package global.citytech.jwt;

public class Application {

    public static void main(String[] args) {
        TokenGenerator tokenGenerator = new TokenGenerator();
        System.out.println(tokenGenerator.generateToken("Ashwin","123"));

    }
}