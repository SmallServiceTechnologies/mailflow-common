package de.flowsuite.mailflow.common.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

class RetryUtil {

    private static final Logger LOG = LoggerFactory.getLogger(RetryUtil.class);

    private static final int MAX_RETRIES = 2;
    private static final long RETRY_DELAY_MS = 1000;

    static <T> T retry(Supplier<T> supplier) {
        int retry = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (Exception e) {
                retry++;
                if (retry > MAX_RETRIES) {
                    LOG.error("API request failed after {} retries", retry, e);
                    throw new RuntimeException(e);
                } else {
                    LOG.warn(
                            "API request failed (retry {} of {}). Retrying in {} seconds...",
                            retry,
                            MAX_RETRIES,
                            (double) RETRY_DELAY_MS * retry / 1000);
                    try {
                        Thread.sleep(RETRY_DELAY_MS * retry);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Retry interrupted", ie);
                    }
                }
            }
        }
    }
}
