package dev.mjamieson.flexspeak.feature.open_ai;

import com.theokanning.openai.image.Image;

import java.util.List;


public record OpenAI_ImageResponse(List<Image> data) {
}
