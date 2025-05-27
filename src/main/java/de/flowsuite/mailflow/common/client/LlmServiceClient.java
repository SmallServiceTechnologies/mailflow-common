package de.flowsuite.mailflow.common.client;

import de.flowsuite.mailflow.common.dto.CategorisationRequest;
import de.flowsuite.mailflow.common.dto.CategorisationResponse;
import de.flowsuite.mailflow.common.dto.GenerationRequest;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Getter
public class LlmServiceClient {

    private static final Logger LOG = LoggerFactory.getLogger(LlmServiceClient.class);

    private static final String POST_CATEGORISATION_URI = "/categorisation";
    private static final String POST_GENERATION_URI = "/generation";

    private final RestClient restClient;

    public LlmServiceClient(@Qualifier("llmServiceRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public CategorisationResponse categorise(CategorisationRequest request) {
        LOG.debug(
                "Calling llm service for user {} to categorise a message...",
                request.user().getId());

        return RetryUtil.retry(
                () ->
                        restClient
                                .post()
                                .uri(POST_CATEGORISATION_URI)
                                .body(request)
                                .retrieve()
                                .body(CategorisationResponse.class));
    }

    public String generateReply(GenerationRequest request) {
        LOG.debug("Calling llm service for user {} to generate a reply...", request.user().getId());

        return RetryUtil.retry(
                () ->
                        restClient
                                .post()
                                .uri(POST_GENERATION_URI)
                                .body(request)
                                .retrieve()
                                .body(String.class));
    }
}
