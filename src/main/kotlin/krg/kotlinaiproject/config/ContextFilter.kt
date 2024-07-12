package krg.kotlinaiproject.config

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import krg.kotlinaiproject.api.Controller
import krg.kotlinaiproject.service.IPService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class ContextFilter(private val ipService: IPService) : WebFilter {
    private val log = LoggerFactory.getLogger(Controller::class.java)

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return mono {
            val request: ServerHttpRequest = exchange.request
            val remoteAddress = request.remoteAddress?.address?.hostAddress
            log.info("filter 실행. 요청 주소 : $remoteAddress")

            if (ipService.isAllowedIp(remoteAddress)) {
                chain.filter(exchange)
                    .contextWrite { it.put("key", "value") }
                    .awaitFirstOrNull()
            } else {
                exchange.response.statusCode = HttpStatus.FORBIDDEN
                log.warn("허가되지 않은 IP 요청 감지. $remoteAddress")
                exchange.response.setComplete().awaitFirstOrNull()
            }
        }
    }
}