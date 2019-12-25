package xyz.mdou.quickstart.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by zhiyong.li
 * Date: 14-11-3.
 * Time: 下午6:09
 */
public class ZxingQrcode {

    private static BufferedImage get2CodeImage(String url) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 300, 300, getEncodeHints());
        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        BufferedImage qrCodeImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrCodeImg.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return qrCodeImg;
    }

    private static Map<EncodeHintType, Object> getEncodeHints() {
        Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);
        return hints;
    }

    private static void addLogo2CodeImg(BufferedImage qrCodeImg, String logo) throws IOException {
        BufferedImage logoImg = ImageIO.read(new File(logo));
        Graphics2D graphics = qrCodeImg.createGraphics();
        int widthLogo = logoImg.getWidth();
        int heightLogo = logoImg.getHeight();
        int x = (qrCodeImg.getWidth() - widthLogo) / 2;
        int y = (qrCodeImg.getHeight() - heightLogo) / 2;

        graphics.drawImage(logoImg, x, y, widthLogo, heightLogo, null);
        graphics.drawRoundRect(x, y, widthLogo, heightLogo, 2, 2);
        graphics.drawRect(x, y, widthLogo, heightLogo);
        graphics.dispose();

        ImageIO.write(qrCodeImg, "png", new File("D:\\qrcode.png"));
    }

    private static String getLogoPath() {
        return ZxingQrcode.class.getResource("/").getPath() + "/logo.jpg";
    }

    public static void main(String[] args) {
        try {
            BufferedImage qrCodeImg = get2CodeImage("http://www.dajie.com");
            addLogo2CodeImg(qrCodeImg, getLogoPath());
            System.out.println("decode qrcode, content=" + decodeQrcode("D:\\qrcode.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<DecodeHintType, Object> getDecodeHints() {
        Map<DecodeHintType, Object> hintTypeObjectMap = new Hashtable<DecodeHintType, Object>();
        hintTypeObjectMap.put(DecodeHintType.CHARACTER_SET, "utf-8");
        return hintTypeObjectMap;
    }

    private static String decodeQrcode(String filePath) {
        String content = "";
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            MultiFormatReader reader = new MultiFormatReader();
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = reader.decode(bitmap, getDecodeHints());
            content = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}
