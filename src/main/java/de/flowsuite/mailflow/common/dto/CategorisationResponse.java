package de.flowsuite.mailflow.common.dto;

import de.flowsuite.mailflow.common.entity.MessageCategory;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategorisationResponse {

    private MessageCategory messageCategory;
    private String llmUsed;
    private Integer inputTokens;
    private Integer outputTokens;
    private Integer totalTokens;

    public CategorisationResponse(MessageCategory messageCategory, String llmUsed, Integer inputTokens, Integer outputTokens, Integer totalTokens) {
        this.messageCategory = messageCategory;
        this.llmUsed = llmUsed;
        this.inputTokens = inputTokens;
        this.outputTokens = outputTokens;
        this.totalTokens = totalTokens;
    }



}
