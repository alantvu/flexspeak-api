package dev.mjamieson.flexspeak.feature.open_ai;

import java.util.List;

public record OpenAI_SuggestionsResponse(String subject, List<String> openAI_Suggestions) {
}
