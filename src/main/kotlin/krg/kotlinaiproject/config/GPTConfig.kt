package krg.kotlinaiproject.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
@Configuration
class GPTConfig {

    @Bean
    fun chatClient(builder: ChatClient.Builder): ChatClient {
        return builder.build()
    }
}