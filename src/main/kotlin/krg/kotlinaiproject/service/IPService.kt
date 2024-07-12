package krg.kotlinaiproject.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class IPService(@Value("\${allowed.ips}") private val allowedIps: List<String>) {

    fun isAllowedIp(ip: String?): Boolean {
        return allowedIps.contains(ip)
    }

}