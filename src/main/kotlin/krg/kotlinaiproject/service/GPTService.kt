package krg.kotlinaiproject.service

import krg.kotlinaiproject.api.res.GPTResponseDTO
import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GPTService(
    @Autowired val chatClient: ChatClient,
    val conversationService: ConversationService
) {


    suspend fun doQuestion(roomId: String, userId:String, message: String): GPTResponseDTO {
        // 레디스의 모든 대화 가져옴
        val conversation = conversationService.getConversation(roomId)?.toMutableList() ?: mutableListOf()
        // 방금한 질문 레디스에 추가
        conversation.add("$userId: $message")

        // 지피티한테 질문하고 답변받기
        val answer = chatClient.prompt()
            .user(refactorQuestion(conversation))
            .call()
            .content()

        // 방금한 질문의 답변 레디스에 추가
        conversation.add("종봇: $answer")

        // 세션 제한. 오래된 기록 제거
        sessionSizeFit(conversation)
        conversationService.saveConversation(roomId,conversation)

        println(answer)
        return GPTResponseDTO(answer)
    }

    private suspend fun sessionSizeFit(conversation: MutableList<String>) {
        if (conversation.size > 200) {
            conversation.subList(0, conversation.size - 200).clear()
        }
    }

    suspend fun refactorQuestion(question: List<String>): String {
        return question.joinToString("\n ")
    }
}