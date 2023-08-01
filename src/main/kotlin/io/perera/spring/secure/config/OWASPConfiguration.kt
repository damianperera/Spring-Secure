package io.perera.spring.secure.config

import io.perera.spring.secure.domain.OWASPHeader
import io.perera.spring.secure.domain.TargetHeaders
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class OWASPConfiguration(
    private val restTemplate: RestTemplate
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @Bean
    @ConditionalOnProperty(prefix = "secure", name = ["enable"], havingValue = "true")
    fun targetHeaders(): TargetHeaders {
        logger.debug("Registering target headers")
        return TargetHeaders(getHeadersToAdd(), getHeadersToRemove())
    }

    private fun getHeadersToAdd(): List<OWASPHeader> {
        logger.debug("Fetching headers to add")
        return restTemplate.getForObject(OWASP_HEADERS_ADD_URL, OWASPAddResponse::class.java)?.headers ?: listOf<OWASPHeader>().also {
            logger.error("Could not fetch target headers from OWASP, some features of this library may not work as expected")
        }
    }

    private fun getHeadersToRemove(): List<OWASPHeader> {
        logger.debug("Fetching headers to remove")
        return restTemplate.getForObject(OWASP_HEADERS_REMOVE_URL, OWASPDeleteResponse::class.java)?.headers?.map {
            OWASPHeader(it, null)
        } ?: listOf<OWASPHeader>().also {
            logger.error("Could not fetch target headers from OWASP, some features of this library may not work as expected")
        }
    }

    private data class OWASPAddResponse(
        val lastUpdateUtc: String,
        val headers: List<OWASPHeader>
    )

    private data class OWASPDeleteResponse (
        val lastUpdatedUtc: String,
        val headers: List<String>
    )

    companion object {
        private const val OWASP_HEADERS_ADD_URL = "https://owasp.org/www-project-secure-headers/ci/headers_add.json"
        private const val OWASP_HEADERS_REMOVE_URL = "https://owasp.org/www-project-secure-headers/ci/headers_remove.json"
    }
}
