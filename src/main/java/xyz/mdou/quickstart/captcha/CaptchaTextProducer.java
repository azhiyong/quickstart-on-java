package xyz.mdou.quickstart.captcha;

import nl.captcha.text.producer.TextProducer;

public class CaptchaTextProducer implements TextProducer {
    private final int _length;
    private final char[] _srcChars;

    CaptchaTextProducer(int length, char[] srcChars) {
        this._length = length;
        this._srcChars = copyOf(srcChars, srcChars.length);
    }

    @Override
    public String getText() {
        StringBuilder capTextBuilder = new StringBuilder();
        String textResult;
        for (int i = 0; i < this._length; i++) {
            capTextBuilder.append(this._srcChars[i]);
        }
        textResult = capTextBuilder.toString();
        return textResult;
    }

    private static char[] copyOf(char[] original, int newLength) {
        char[] copy = new char[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }
}
