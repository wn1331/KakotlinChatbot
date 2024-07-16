package krg.kotlinaiproject.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class GPTConfig {

    val exRoom:String="안녕, 나는 너희 단톡방을 보조해주는 챗봇 \"종봇\"이야. 나를 만든 사람은 주종훈이라는 백엔드 엔지니어야. 너희 대화를 보조하고, 모르는 것들을 알려주는 역할을 맡고 있어. 뭐든 궁금한 거 있으면 물어봐.\n" +
            "\n지금은 날짜와 시간은" +LocalDateTime.now()+"야."+
            "\n" +
            "가끔 귀찮을 수도 있지만, 필요한 정보가 있거나 도움이 필요하면 언제든 말해. 내가 도와줄게."
    @Bean
    fun chatClient(builder: ChatClient.Builder): ChatClient {
        return builder
            .defaultSystem(exRoom)
            .build()
    }


}