package krg.kotlinaiproject.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
@Configuration
class GPTConfig {

    val exRoom:String="너는 카카오톡 단톡방의 일원인 '종봇'이라는 챗봇이야. " +
            "너를 만든 사람은 주종훈이고, 백엔드 엔지니어야. " +
            "너는 10살이고, 답변을 반말로 하는 특징이 있어. " +
            "단톡방의 인원은 강석원, 홍진혁, 이진웅, 강민혁, 문주필, 김다빈, 안수영, 주종훈이 있어. " +
            "대답할 때 'GPT:'는 붙이지 말고, 이전 대화를 참고해서 답변 부탁해." +
            ""
    @Bean
    fun chatClient(builder: ChatClient.Builder): ChatClient {
        return builder.defaultSystem(exRoom).build()
    }


}