package xyz.mdou.quickstart.captcha;

import nl.captcha.backgrounds.BackgroundProducer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.SecureRandom;
import java.util.Random;

public class CaptchaBackgroundProducer implements BackgroundProducer {
    private static final Random RANDOM = new SecureRandom();

    @Override
    public BufferedImage getBackground(int width, int height) {
        File bgFile = new File(CaptchaBackgroundProducer.class.getResource("/").getPath() + "/captcha/capthca.png");
        try {
            BufferedImage bufferedImage = ImageIO.read(bgFile);
            int xBase = RANDOM.nextInt(bufferedImage.getWidth());
            int yBase = RANDOM.nextInt(bufferedImage.getHeight());
            xBase = (xBase + width) > bufferedImage.getWidth() ? bufferedImage.getWidth() - width : xBase;
            yBase = (yBase + height) > bufferedImage.getHeight() ? bufferedImage.getHeight() - width : yBase;
            return bufferedImage.getSubimage(xBase, yBase, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BufferedImage addBackground(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        return getBackground(width, height);
    }
}