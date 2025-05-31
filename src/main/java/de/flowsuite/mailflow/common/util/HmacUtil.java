package de.flowsuite.mailflow.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HmacUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HmacUtil.class);

    private static final SecretKey key = loadHmacKey();

    private static SecretKey loadHmacKey() {
        String envVar = "HMAC_B64_SECRET_KEY";

        String b64SecretKey = System.getenv(envVar);
        if (b64SecretKey == null || b64SecretKey.isBlank()) {
            LOG.warn("{} environment variable is missing.", envVar);
            return null;
        }

        byte[] decodedKey = Base64.getDecoder().decode(b64SecretKey);
        return new SecretKeySpec(decodedKey, "HmacSHA256");
    }

    public static String hash(String input) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            byte[] hash = mac.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
