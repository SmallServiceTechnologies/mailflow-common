package de.flowsuite.mailflow.common.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class RestClientConfig {

    private final String apiBaseUrl;
    private final String mailboxServiceBaseUrl;
    private final String llmServiceBaseUrl;
    private final String ragServiceBaseUrl;
    private final JwtRestClientInterceptor jwtRestClientInterceptor;

    RestClientConfig(
            @Value("${mailflow.api.base-url}") String apiBaseUrl,
            @Value("${mailflow.mailbox-service.base-url}") String mailboxServiceBaseUrl,
            @Value("${mailflow.llm-service.base-url}") String llmServiceBaseUrl,
            @Value("${mailflow.rag-service.base-url}") String ragServiceBaseUrl,
            JwtRestClientInterceptor jwtRestClientInterceptor) {
        this.apiBaseUrl = apiBaseUrl;
        this.mailboxServiceBaseUrl = mailboxServiceBaseUrl;
        this.llmServiceBaseUrl = llmServiceBaseUrl;
        this.ragServiceBaseUrl = ragServiceBaseUrl;
        this.jwtRestClientInterceptor = jwtRestClientInterceptor;
    }

    @Bean
    public RestClient apiRestClient() {
        return RestClient.builder()
                .baseUrl(apiBaseUrl)
                .requestInterceptor(jwtRestClientInterceptor)
                .build();
    }

    @Bean
    public RestClient mailboxServiceRestClient() {
        return RestClient.builder()
                .baseUrl(mailboxServiceBaseUrl)
                .requestInterceptor(jwtRestClientInterceptor)
                .build();
    }

    @Bean
    public RestClient llmServiceRestClient() {
        return RestClient.builder()
                .baseUrl(llmServiceBaseUrl)
                .requestInterceptor(jwtRestClientInterceptor)
                .build();
    }

    @Bean
    public RestClient ragServiceRestClient() {
        return RestClient.builder()
                .baseUrl(ragServiceBaseUrl)
                .requestInterceptor(jwtRestClientInterceptor)
                .build();
    }
}
