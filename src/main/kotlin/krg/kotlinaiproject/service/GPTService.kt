package krg.kotlinaiproject.service

import krg.kotlinaiproject.api.res.GPTResponseDTO
import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GPTService(@Autowired val chatClient: ChatClient, val conversationService: ConversationService) {



    suspend fun doQuestion(message: String): GPTResponseDTO {
        // 레디스의 모든 대화 가져옴
        val conversation = conversationService.getConversation()?.toMutableList() ?: mutableListOf()
        // 방금한 질문 레디스에 추가
        conversation.add("User: $message")

        // 지피티한테 질문하고 답변받기
        val answer = chatClient.prompt()
            .user(refactorQuestion(conversation))
            .call()
            .content()

        // 방금한 질문의 답변 레디스에 추가
        conversation.add("GPT: $answer")

        conversationService.saveConversation(conversation)
        // 세션 제한. 오래된 기록 제거
        sessionSizeFit(conversation)
        return GPTResponseDTO(answer)
    }

    private fun sessionSizeFit(conversation: MutableList<String>) {
        if (conversation.size > 100) {
            conversation.subList(0, conversation.size - 100).clear()
        }
    }

    suspend fun refactorQuestion(question: List<String>): String{
        val prompt = question.joinToString("\n")

        return prompt+"\n\n\n " +"위 대화내용을 토대로 프롬프트의 마지막 말에 대한 답변을 하면 돼. 그리고 반복되는 답변은 절대 하지 마.\n\n\n"+
                "<답변은 특별한 질문이 있지 않은 이상 무조건 최대한 짧고 간결하게 해. 그리고 너의 이름을 물어봤을 때, 이전 프롬프트에 내용이 비었거나,너의 이름에 관한 내용이 없다면 너무 오래되서 이름이 기억나지 않는다고 해.(이 말들은 답변하지 마)>"
    }
}