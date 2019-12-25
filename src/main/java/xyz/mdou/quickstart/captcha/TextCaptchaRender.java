package xyz.mdou.quickstart.captcha;

import nl.captcha.text.renderer.WordRenderer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TextCaptchaRender implements WordRenderer {
    private static final Random RANDOM = new SecureRandom();
    private static final List<Color> DEFAULT_COLORS = new LinkedList<Color>();
    private static final List<Font> DEFAULT_FONTS = new LinkedList<Font>();
    private final List<Color> colors = new LinkedList<Color>();
    private final List<Font> fonts = new LinkedList<Font>();

    static {
        DEFAULT_COLORS.add(Color.BLACK);
        DEFAULT_FONTS.add(new Font("宋体", Font.BOLD, 19));
        DEFAULT_FONTS.add(new Font("隶书", Font.BOLD, 19));
    }

    public TextCaptchaRender() {
        this(DEFAULT_COLORS, DEFAULT_FONTS);
    }

    TextCaptchaRender(List<Color> colors, List<Font> fonts) {
        this.colors.addAll(colors);
        this.fonts.addAll(fonts);
    }

    @Override
    public void render(String word, BufferedImage image) {
        Graphics2D g2D = image.createGraphics();
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2D.setRenderingHints(hints);

        char wordChars[] = word.toCharArray();
        Font chosenFonts[] = new Font[wordChars.length];
        for (int i = 0; i < wordChars.length; i++) {
            chosenFonts[i] = this.fonts.get(RANDOM.nextInt(this.fonts.size()));
        }
        for (int i = 0; i < wordChars.length; i++) {
            //设置字体
            int fontSize = chosenFonts[i].getSize();
            g2D.setFont(chosenFonts[i]);
            //设置颜色
            g2D.setColor(this.colors.get(RANDOM.nextInt(this.colors.size())));
            AffineTransform affine = new AffineTransform();
            double x = (RANDOM.nextInt(60) - 30) * 0.001;
            double y = (RANDOM.nextInt(40) - 20) * 0.001;
            affine.scale(1 + x, 1 + y);
            affine.rotate(Math.PI / 4 * (RANDOM.nextInt(8) * 0.1) * (RANDOM.nextBoolean() ? 1 : -1), (image.getWidth() / wordChars.length) * i + fontSize / 2, image.getHeight() / 2);
            g2D.setTransform(affine);
            g2D.drawChars(wordChars, i, 1, (int) (((image.getWidth() - RANDOM.nextInt(5)) / wordChars.length) * i * 0.78 + 7), image.getHeight() / 2 + fontSize / 2 - 1);
        }
    }
}