package xyz.mdou.quickstart.encrypt;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import sun.misc.BASE64Decoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class HmacMd5 {

    /**
     * MAC算法可选以下多种算法
     * <p/>
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    private static final String KEY_MAC = "HmacMD5";

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static String encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return byte2hex(mac.doFinal(data));
    }

    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
            if (n < b.length - 1) {
                hs.append(":");
            }
        }
        return hs.toString().toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        String userinfo = "{\"name\":\"欧昀熙\"}";
        String key = "123456";
        String hash = HmacMd5.encryptHMAC(userinfo.getBytes("UTF-8"), key);
        Request.Post("http://si.sankai.com/v1/verifi/?hash=" + hash)
                .bodyForm(Form.form()
                        .add("userinfo", userinfo).build())
                .execute()
                .handleResponse((ResponseHandler<Object>) httpResponse -> {
                    System.out.println(httpResponse.getStatusLine().getStatusCode());
                    StringBuilder sb = new StringBuilder();
                    HttpEntity entity = httpResponse.getEntity();
                    String contentType = entity.getContentType().getValue();
                    String charset = contentType.replaceAll("([^;]*);\\s*charset=", "");
                    try {
                        if (!Charset.isSupported(charset)) {
                            charset = "UTF-8";
                            System.out.print("charset not supported:" + charset);
                        }
                    } catch (Exception e) {
                        charset = "UTF-8";
                    }
                    InputStream in = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
                    String s;
                    while ((s = reader.readLine()) != null) {
                        sb.append(s).append("\r\n");
                    }
                    System.out.println(sb.toString());
                    return sb.toString();
                });
    }
}
