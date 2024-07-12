package krg.kotlinaiproject.api

import kotlinx.coroutines.reactor.ReactorContext
import krg.kotlinaiproject.api.res.GPTResponseDTO
import krg.kotlinaiproject.service.ConversationService
import krg.kotlinaiproject.service.GPTService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping("/cor")
    suspend fun hello(
        @RequestParam(required = true) message: String
    ): GPTResponseDTO {



        coroutineContext[ReactorContext]?.context
        return gptService.doQuestion(message)
    }
}