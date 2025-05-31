package de.flowsuite.mailflow.common.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AesUtilTest {

    private static final String PLAIN_TEXT = "This is a secret message";

    @Test
    @Disabled
    void testEncryptAndDecrypt_success() {
        String encrypted = AesUtil.encrypt(PLAIN_TEXT);

        assertNotNull(encrypted);
        assertNotEquals(PLAIN_TEXT, encrypted);

        String decrypted = AesUtil.decrypt(encrypted);

        assertEquals(PLAIN_TEXT, decrypted);
    }

    @Test
    @Disabled
    void testEncrypt_nullPlainText_throwsException() {
        assertThrows(RuntimeException.class, () -> AesUtil.encrypt(null));
    }

    @Test
    @Disabled
    void testDecrypt_nullEncryptedText_throwsException() {
        assertThrows(RuntimeException.class, () -> AesUtil.decrypt(null));
    }

    @Test
    @Disabled
    void testDecrypt_invalidEncryptedText_throwsException() {
        assertThrows(RuntimeException.class, () -> AesUtil.decrypt(PLAIN_TEXT));
    }
}
