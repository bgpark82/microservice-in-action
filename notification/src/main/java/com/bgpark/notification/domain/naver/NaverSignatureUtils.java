package com.bgpark.notification.domain.naver;

import com.bgpark.notification.util.Constant;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class NaverSignatureUtils {

    public static String createSignature(String path, String method, String timestamp, String accessKey, String secretKey) {
        String message = createSignatureMessage(path, method, timestamp, accessKey);
        byte[] rawHmac = createRawHmac(secretKey, message);
        return Base64.encodeBase64String(rawHmac);
    }

    private static byte[] createRawHmac(String secretKey, String message) {
        try {
            Mac mac = Mac.getInstance(Constant.HMAC_SHA256);
            mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Constant.HMAC_SHA256));
            return mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createSignatureMessage(String path, String method, String timestamp, String accessKey) {
        return new StringBuilder()
                .append(method)
                .append(Constant.SPACE)
                .append(path)
                .append(Constant.NEW_LINE)
                .append(timestamp)
                .append(Constant.NEW_LINE)
                .append(accessKey)
                .toString();
    }
}
