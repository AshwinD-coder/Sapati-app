package global.citytech.user.service.qr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

public class UserQrService {

    public static void generateQR(UserQrRequest userQrRequest){
        try {
            String json = new ObjectMapper().writeValueAsString(userQrRequest);
            String username = userQrRequest.getUsername();
            String pathToQR = "C:\\Users\\Administrator\\IdeaProjects\\myapp\\src\\main\\java\\global\\citytech\\platform\\userqrfiles\\"+username+"_qr.jpg";
            BitMatrix matrix = new MultiFormatWriter().encode(json, BarcodeFormat.QR_CODE,200,200);
            MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(pathToQR));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static UserQrRequest readQR(String fileName){
        try {
            BufferedImage qr = ImageIO.read(new File("C:\\Users\\Administrator\\IdeaProjects\\myapp\\src\\main\\java\\global\\citytech\\platform\\userqrfiles\\"+fileName));
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(qr)));
            Result result = new MultiFormatReader().decode(bitmap);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(result.getText(), UserQrRequest.class);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
