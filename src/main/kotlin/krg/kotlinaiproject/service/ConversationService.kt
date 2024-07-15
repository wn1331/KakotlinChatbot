package krg.kotlinaiproject.service
import krg.kotlinaiproject.api.res.RedisResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class ConversationService @Autowired constructor(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    private val archiveKey = "room: "

    fun saveConversation(roomId:String, conversation: List<String>) {
        redisTemplate.opsForValue().set(archiveKey+roomId, conversation)
    }

    fun getConversation(roomId: String): List<String>? {
        return redisTemplate.opsForValue().get(archiveKey+roomId) as? List<String>
    }

    fun clearConversation(roomId: String) :RedisResponseDTO{
        redisTemplate.delete(archiveKey+roomId)
        return RedisResponseDTO("세션이 초기화되었습니다.")
    }

}
