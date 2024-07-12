package krg.kotlinaiproject.service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class ConversationService @Autowired constructor(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    private val archiveKey = "chat:archive"

    fun saveConversation(conversation: List<String>) {
        redisTemplate.opsForValue().set(archiveKey, conversation)
    }

    fun getConversation(): List<String>? {
        return redisTemplate.opsForValue().get(archiveKey) as? List<String>
    }

    fun clearConversation() {
        redisTemplate.delete(archiveKey)
    }
}
