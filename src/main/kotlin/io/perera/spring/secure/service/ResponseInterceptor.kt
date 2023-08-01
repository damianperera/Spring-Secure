package io.perera.spring.secure.service

import io.perera.spring.secure.domain.OWASPHeader
import io.perera.spring.secure.domain.TargetHeaders
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

class ResponseInterceptor: HandlerInterceptor {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private var targetHeaders: TargetHeaders

    init {
        targetHeaders = OWASPFetch().getTargetHeaders()
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        response.addHeaders()
        response.removeHeaders()
        super.postHandle(request, response, handler, modelAndView).also {
            logger.debug("Successfully modified headers")
        }
    }

    private fun HttpServletResponse.addHeaders() = targetHeaders.headersToAdd.forEach {
        this.addHeader(it.name, it.value)
    }

    private fun HttpServletResponse.removeHeaders() = targetHeaders.headersToRemove.forEach {
        this.setHeader(it.name, null)
    }

    internal class OWASPFetch {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private val restTemplate = RestTemplate()
        fun getTargetHeaders(): TargetHeaders {
            return TargetHeaders(getHeadersToAdd(), getHeadersToRemove()).also {
                logger.debug("OWASP target headers: {}", it)
            }
        }

        private fun getHeadersToAdd(): List<OWASPHeader> {
            return restTemplate.getForObject(OWASP_HEADERS_ADD_URL, OWASPAddResponse::class.java)?.headers ?: listOf<OWASPHeader>().also {
                logger.error("Could not fetch target headers from OWASP, some features of this library may not work as expected")
            }
        }

        private fun getHeadersToRemove(): List<OWASPHeader> {
            return restTemplate.getForObject(OWASP_HEADERS_REMOVE_URL, OWASPDeleteResponse::class.java)?.headers?.map {
                OWASPHeader(it, null)
            } ?: listOf<OWASPHeader>().also {
                logger.error("Could not fetch target headers from OWASP, some features of this library may not work as expected")
            }
        }

        data class OWASPAddResponse(
            var lastUpdateUtc: String?,
            var headers: List<OWASPHeader>?
        )

        data class OWASPDeleteResponse (
            var lastUpdateUtc: String?,
            var headers: List<String>?
        )

        private companion object {
            private const val OWASP_HEADERS_ADD_URL = "https://owasp.org/www-project-secure-headers/ci/headers_add.json"
            private const val OWASP_HEADERS_REMOVE_URL = "https://owasp.org/www-project-secure-headers/ci/headers_remove.json"
        }
    }
}
