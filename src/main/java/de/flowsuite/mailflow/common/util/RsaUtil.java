package de.flowsuite.mailflow.common.util;

import de.flowsuite.mailflow.common.exception.MissingEnvVarException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtil {

    private static final Logger LOG = LoggerFactory.getLogger(RsaUtil.class);

    public static final RSAPublicKey publicKey = loadPublicKey();
    public static final RSAPrivateKey privateKey = loadPrivateKey();

    public RsaUtil() {}

    private static RSAPrivateKey loadPrivateKey() {
        String envVar = "RSA_PRIVATE_KEY";

        String key = System.getenv(envVar);
        if (key == null || key.isBlank()) {
            LOG.warn("{} environment variable is missing.", envVar);
            return null;
        }

        try {
            key =
                    key.replace("-----BEGIN PRIVATE KEY-----", "")
                            .replace("-----END PRIVATE KEY-----", "")
                            .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static RSAPublicKey loadPublicKey() {
        String envVar = "RSA_PUBLIC_KEY";

        String key = System.getenv(envVar);
        if (key == null || key.isBlank()) {
            LOG.warn("{} environment variable is missing.", envVar);
            return null;
        }

        try {
            key =
                    key.replace("-----BEGIN PUBLIC KEY-----", "")
                            .replace("-----END PUBLIC KEY-----", "")
                            .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
