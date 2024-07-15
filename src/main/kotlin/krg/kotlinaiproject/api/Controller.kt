package krg.kotlinaiproject.api

import kotlinx.coroutines.reactor.ReactorContext
import krg.kotlinaiproject.api.res.GPTResponseDTO
import krg.kotlinaiproject.api.res.RedisResponseDTO
import krg.kotlinaiproject.service.ConversationService
import krg.kotlinaiproject.service.GPTService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.coroutineContext

@RestController
// @Autowired는 생성자가 한개라면 생략이 가능하다.
class Controller(
    @Autowired
    val gptService: GPTService,
    val conversationService: ConversationService
) {

    @PostMapping("/cor/{roomId}/{userId}")
    suspend fun hello(
        @PathVariable roomId: String,
        @PathVariable userId: String,
        @RequestParam(required = true) message: String
    ): GPTResponseDTO {

        coroutineContext[ReactorContext]?.context
        return gptService.doQuestion(roomId, userId, message)
    }

    @GetMapping("/clear/{roomId}")
    suspend fun clear(@PathVariable roomId: String): RedisResponseDTO {
        coroutineContext[ReactorContext]?.context
        return conversationService.clearConversation(roomId)
    }
}