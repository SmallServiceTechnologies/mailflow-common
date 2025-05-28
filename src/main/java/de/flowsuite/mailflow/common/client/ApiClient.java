package de.flowsuite.mailflow.common.client;

import static de.flowsuite.mailflow.common.util.Util.BERLIN_ZONE;

import de.flowsuite.mailflow.common.dto.CategorisationResponse;
import de.flowsuite.mailflow.common.dto.CreateMessageLogEntryRequest;
import de.flowsuite.mailflow.common.dto.LlmResponse;
import de.flowsuite.mailflow.common.dto.UpdateCustomerCrawlStatusRequest;
import de.flowsuite.mailflow.common.entity.*;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@Getter
public class ApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(ApiClient.class);

    private static final String LIST_USERS_URI = "/customers/users";
    private static final String LIST_USERS_BY_CUSTOMER_URI = "/customers/{customerId}/users";
    private static final String GET_CUSTOMER_URI = "/customers/{customerId}";
    private static final String GET_CUSTOMER_TEST_VERSION_URI =
            "/customers/{customerId}/test-version";
    private static final String LIST_CUSTOMERS_URI = "/customers";
    private static final String PUT_CUSTOMER_CRAWL_STATUS_URI = "/customers/{customerId}/crawl-status";
    private static final String LIST_MESSAGE_CATEGORIES_URI =
            "/customers/{customerId}/message-categories";
    private static final String LIST_BLACKLIST_URI =
            "/customers/{customerId}/users/{userId}/blacklist";
    private static final String LIST_RAG_URLS_URI = "/customers/{customerId}/rag-urls";
    private static final String PUT_RAG_URL_CRAWL_STATUS_URI = "/customers/{customerId}/rag-urls/{id}/crawl-status";
    private static final String POST_MESSAGE_LOG_ENTRY_URI =
            "/customers/{customerId}/users/{userId}/message-log";

    private final RestClient restClient;

    public ApiClient(@Qualifier("apiRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<User> listUsers() {
        LOG.debug("Listing users");

        return RetryUtil.retry(
                () ->
                        restClient
                                .get()
                                .uri(LIST_USERS_URI)
                                .retrieve()
                                .body(new ParameterizedTypeReference<List<User>>() {}));
    }

    public List<User> listUsersByCustomer(long customerId) {
        LOG.debug("Listing users for customer {}", customerId);

        return RetryUtil.retry(
                () ->
                        restClient
                                .get()
                                .uri(LIST_USERS_BY_CUSTOMER_URI, customerId)
                                .retrieve()
                                .body(new ParameterizedTypeReference<List<User>>() {}));
    }

    public Customer getCustomer(long customerId) {
        LOG.debug("Fetching customer {}", customerId);

        return RetryUtil.retry(
                () ->
                        restClient
                                .get()
                                .uri(GET_CUSTOMER_URI, customerId)
                                .retrieve()
                                .body(Customer.class));
    }

    public Boolean isCustomerTestVersion(long customerId) {
        LOG.debug("Fetching customer test version {}", customerId);

        return RetryUtil.retry(
                () ->
                        restClient
                                .get()
                                .uri(GET_CUSTOMER_TEST_VERSION_URI, customerId)
                                .retrieve()
                                .body(Boolean.class));
    }

    public List<Customer> listCustomers() {
        LOG.debug("Listing customers");

        return RetryUtil.retry(
                () ->
                        restClient
                                .get()
                                .uri(LIST_CUSTOMERS_URI)
                                .retrieve()
                                .body(new ParameterizedTypeReference<List<Customer>>() {}));
    }

    public void updateCustomerCrawlStatus(UpdateCustomerCrawlStatusRequest request) {
        LOG.debug("Updating customer {}", request.id());

        RetryUtil.retry(
                () -> {
                    restClient
                            .put()
                            .uri(PUT_CUSTOMER_CRAWL_STATUS_URI, request.id())
                            .body(request)
                            .retrieve()
                            .toBodilessEntity();
                    return true;
                });
    }

    public List<MessageCategory> listMessageCategories(long customerId) {
        LOG.debug("Listing message categories for customer {}", customerId);

        return RetryUtil.retry(
                () ->
                        restClient
                                .get()
                                .uri(LIST_MESSAGE_CATEGORIES_URI, customerId)
                                .retrieve()
                                .body(new ParameterizedTypeReference<List<MessageCategory>>() {}));
    }

    public List<BlacklistEntry> listBlacklistEntries(long customerId, long userId) {
        LOG.debug("Fetching blacklist for user {}", userId);

        return RetryUtil.retry(
                () ->
                        restClient
                                .get()
                                .uri(LIST_BLACKLIST_URI, customerId, userId)
                                .retrieve()
                                .body(new ParameterizedTypeReference<List<BlacklistEntry>>() {}));
    }

    public List<RagUrl> listRagUrls(long customerId) {
        LOG.debug("Fetching rag urls for customer {}", customerId);

        return RetryUtil.retry(
                () ->
                        restClient
                                .get()
                                .uri(LIST_RAG_URLS_URI, customerId)
                                .retrieve()
                                .body(new ParameterizedTypeReference<List<RagUrl>>() {}));
    }

    public void updateRagUrlCrawlStatus(long customerId, long ragUrlId, boolean lastCrawlSuccessful) {
        LOG.debug("Updating rag url {} for customer {}", ragUrlId, customerId);

        RetryUtil.retry(
                () -> {
                    restClient
                            .put()
                            .uri(PUT_RAG_URL_CRAWL_STATUS_URI, customerId, ragUrlId)
                            .body(lastCrawlSuccessful)
                            .retrieve()
                            .toBodilessEntity();
                    return true;
                });
    }

    public MessageLogEntry createMessageLogEntry(
            long customerId,
            long userId,
            String fromEmailAddress,
            String subject,
            ZonedDateTime receivedAt,
            CategorisationResponse categorisationResponse,
            LlmResponse generationResponse,
            MessageCategory messageCategory) {
        LOG.debug("Creating message log entry for user {}", userId);

        ZonedDateTime now = ZonedDateTime.now(BERLIN_ZONE);
        int processingTime = (int) Duration.between(receivedAt, now).getSeconds();

        boolean replied = generationResponse != null;

        String generationModelName =
                generationResponse != null ? generationResponse.llmUsed() : null;
        Integer generationInputTokens =
                generationResponse != null ? generationResponse.inputTokens() : null;
        Integer generationOutputTokens =
                generationResponse != null ? generationResponse.outputTokens() : null;
        Integer generationTotalTokens =
                generationResponse != null ? generationResponse.totalTokens() : null;

        CreateMessageLogEntryRequest request =
                new CreateMessageLogEntryRequest(
                        userId,
                        customerId,
                        replied,
                        messageCategory.getFunctionCall(),
                        messageCategory.getCategory(),
                        null, // TODO
                        fromEmailAddress,
                        subject,
                        receivedAt,
                        now,
                        processingTime,
                        categorisationResponse.llmUsed(),
                        categorisationResponse.inputTokens(),
                        categorisationResponse.outputTokens(),
                        categorisationResponse.totalTokens(),
                        generationModelName,
                        generationInputTokens,
                        generationOutputTokens,
                        generationTotalTokens);

        return RetryUtil.retry(
                () ->
                        restClient
                                .post()
                                .uri(POST_MESSAGE_LOG_ENTRY_URI, customerId, userId)
                                .body(request)
                                .retrieve()
                                .body(MessageLogEntry.class));
    }
}
