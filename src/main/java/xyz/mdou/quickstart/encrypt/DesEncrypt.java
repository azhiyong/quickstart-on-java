package xyz.mdou.quickstart.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class DesEncrypt {
    private final static String KEY = "12345678";

    private static byte[] convertHexString(String data) {
        byte[] digest = new byte[data.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = data.substring(2 * i, 2 * i + 2);
            if (!Objects.equals(byteString.trim(), "")) {
                int byteValue = Integer.parseInt(byteString, 16);
                digest[i] = (byte) byteValue;
            }
        }
        return digest;
    }

    private static String toHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte aData : data) {
            String painText = Integer.toHexString(0xff & aData);
            if (painText.length() < 2) {
                painText = "0" + painText;
            }
            sb.append(painText);
        }
        return sb.toString();
    }

    private static byte[] encrypt(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec keySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decrypt(String data, String key) {
        byte[] bytes = convertHexString(data);
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec keySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] resultByte = cipher.doFinal(bytes);
            return new String(resultByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            String plainText = "lizhiyong";
            byte[] bytes = encrypt(URLEncoder.encode(plainText, "UTF-8"), KEY);

            String encodeText = toHexString(bytes);
            System.out.println(plainText + "->" + encodeText);

            //解密
            String decodeText = URLDecoder.decode(decrypt(encodeText, KEY), "UTF-8");
            System.out.println(encodeText + "->" + decodeText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
