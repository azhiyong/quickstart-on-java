package xyz.mdou.quickstart.captcha;

import com.google.common.io.Files;
import nl.captcha.Captcha;
import nl.captcha.text.renderer.WordRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GenerateCaptcha {
    private static final int DEFAULT_CAPTCHA_IMAGE_WIDTH = 80;
    private static final int DEFAULT_CAPTCHA_IMAGE_HEIGHT = 34;
    private static final int DEFAULT_CAPTCHA_LENGTH = 4;
    private static final Random random = new Random();
    private static final java.util.List<String> words_list = new ArrayList<String>();
    private static final Font[] DEFAULT_FONT_WEI_LIST = {
            new Font("msyh", Font.ITALIC, 17),
            new Font("msyh", Font.ITALIC, 18),
            new Font("msyh", Font.PLAIN, 19),
    };
    private static final Font[] DEFAULT_FONT_SONG_LIST = {
            new Font("simsunb", Font.BOLD, 17),
            new Font("simsunb", Font.ITALIC, 18),
            new Font("simsunb", Font.PLAIN, 19),
    };
    private static final Color[] DEFAULT_COLOR_LIST = {
            new Color(0xcd006e), new Color(0x0b1aac), new Color(0x333333),
            new Color(0x0059bd), new Color(0x004eff)
    };

    private static void init() {
        try {
            File file = new File(GenerateCaptcha.class.getResource("/").getPath() + "/captcha/word.txt");
            if (file.exists()) {
                words_list.addAll(Files.readLines(file, Charset.forName("utf-8")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        init();
        //自定义设置字体颜色和大小 最简单的效果 多种字体随机显示
        Color color = DEFAULT_COLOR_LIST[random.nextInt(DEFAULT_COLOR_LIST.length)];
        WordRenderer wordRenderer = new TextCaptchaRender(Collections.singletonList(color), Arrays.asList(random.nextInt(2) == 0 ? DEFAULT_FONT_SONG_LIST : DEFAULT_FONT_WEI_LIST));
        //自定义验证码背景
        CaptchaBackgroundProducer captchaBackground = new CaptchaBackgroundProducer();
        Captcha.Builder builder = new Captcha.Builder(DEFAULT_CAPTCHA_IMAGE_WIDTH, DEFAULT_CAPTCHA_IMAGE_HEIGHT);
        Captcha captcha = builder.addBackground(captchaBackground).build(); //设置背景
        String stringSet = words_list.get(random.nextInt(words_list.size())).trim();

        char[] chars = new char[stringSet.length()];
        for (int i = 0; i < stringSet.length(); i++) {
            chars[i] = stringSet.charAt(i);
        }
        builder.addText(new CaptchaTextProducer(DEFAULT_CAPTCHA_LENGTH, chars), wordRenderer); //设置文本
        drawLine(captcha.getImage(), color, random.nextInt(5) + 15);
        ImageIO.write(captcha.getImage(), "png", new File("D:\\captcha-code.png"));
    }

    private static void drawLine(BufferedImage image, Color color, int offset) {
        Graphics g = image.getGraphics();
        g.setColor(color);
        int rate = random.nextInt(80) - 40;
        double y = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            y = Math.cos(x / 12.0) * rate / 5 + offset;
            image.setRGB(x, (int) y, color.getRGB());
            if (x + 1 < image.getWidth()) {
                y = Math.cos((x + 1) / 12.0) * rate / 5 + offset;
                image.setRGB(x + 1, (int) y, color.getRGB());
            }

        }
    }
}
