package de.flowsuite.mailflowcommon.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RsaUtilTest {

    @Test
    void testLoadPrivateKey() {
        assertNotNull(RsaUtil.privateKey);
    }

    @Test
    void testLoadPublicKey() {
        assertNotNull(RsaUtil.publicKey);
    }
}
