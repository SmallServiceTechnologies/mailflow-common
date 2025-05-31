package de.flowsuite.mailflow.common.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class HmacUtilTest {

    private static final String input = "someInputString";

    @Test
    @Disabled
    void hash_validInput_success() {
        String hash = HmacUtil.hash(input);

        assertNotNull(hash);
        assertFalse(hash.isBlank());
    }

    @Test
    @Disabled
    void hash_sameInput_sameHash() {
        String hash1 = HmacUtil.hash(input);
        String hash2 = HmacUtil.hash(input);

        assertNotNull(hash1);
        assertFalse(hash1.isBlank());

        assertNotNull(hash2);
        assertFalse(hash2.isBlank());

        assertEquals(hash1, hash2);
    }

    @Test
    @Disabled
    void hash_differentInput_differentHash() {
        String input1 = "inputOne";
        String input2 = "inputTwo";

        String hash1 = HmacUtil.hash(input1);
        String hash2 = HmacUtil.hash(input2);

        assertNotEquals(hash1, hash2);
    }
}
