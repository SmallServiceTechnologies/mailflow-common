package de.flowsuite.mailflow.common.client;

import de.flowsuite.mailflow.common.dto.*;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Getter
public class RagServiceClient {

    private static final Logger LOG = LoggerFactory.getLogger(RagServiceClient.class);

    private static final String POST_SEARCH_URI = "/search";

    private final RestClient restClient;

    public RagServiceClient(@Qualifier("ragServiceRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public RagServiceResponse search(RagServiceRequest request) {
        LOG.debug(
                "Calling rag service for user {} to find relevant embeddings...", request.userId());

        return RetryUtil.retry(
                () ->
                        restClient
                                .post()
                                .uri(POST_SEARCH_URI)
                                .body(request)
                                .retrieve()
                                .body(RagServiceResponse.class));
    }
}
