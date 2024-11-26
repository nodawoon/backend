package nodawoon.me_to_you.global.chatGpt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
public class ChatGptConfig {

    @Value("${api.openai-key}")
    String openaiApiKey;

    @Bean
    public WebClient openaiWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.openai.com")
                .defaultHeader("Authorization", "Bearer " + openaiApiKey)
                .defaultHeader("content-type", "application/json")
                .build();
    }
}
