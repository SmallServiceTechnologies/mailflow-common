package de.flowsuite.mailflow.common.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

class RetryUtil {

    private static final Logger LOG = LoggerFactory.getLogger(RetryUtil.class);

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    static <T> T retry(Supplier<T> supplier) {
        int attempt = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (Exception e) {
                attempt++;
                if (attempt >= MAX_RETRIES) {
                    LOG.error("API request failed after {} attempts", attempt, e);
                    throw new RuntimeException(e);
                } else {
                    LOG.warn(
                            "API request failed (attempt {} of {}). Retrying in {} seconds...",
                            attempt,
                            MAX_RETRIES,
                            (float) RETRY_DELAY_MS * attempt / 1000);
                    try {
                        Thread.sleep(RETRY_DELAY_MS * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Retry interrupted", ie);
                    }
                }
            }
        }
    }
}
