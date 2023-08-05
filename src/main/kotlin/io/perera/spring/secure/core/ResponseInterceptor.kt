package io.perera.spring.secure.core

import io.perera.spring.secure.domain.OWASPHeader
import io.perera.spring.secure.domain.TargetHeaders
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

/**
 * Outgoing HTTP Response Interceptor.
 */
class ResponseInterceptor : HandlerInterceptor {
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
            logger.debug("Applied OWASP header recommendations")
        }
    }

    /**
     * Extension function used to add the recommended [OWASPHeader]s to [HttpServletResponse].
     */
    private fun HttpServletResponse.addHeaders() = targetHeaders.headersToAdd.forEach {
        this.addHeader(it.name, it.value)
    }

    /**
     * Extension function used to remove the insecure [OWASPHeader]s to [HttpServletResponse].
     */
    private fun HttpServletResponse.removeHeaders() = targetHeaders.headersToRemove.forEach {
        this.setHeader(it.name, null)
    }

    /**
     * Class used to dynamically fetch [TargetHeaders] from OWASP.
     */
    internal class OWASPFetch {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private val restTemplate = RestTemplate()

        /**
         * Returns the [TargetHeaders] from OWASP.
         */
        fun getTargetHeaders(): TargetHeaders {
            return TargetHeaders(getHeadersToAdd(), getHeadersToRemove()).also {
                logger.debug("Retrieved OWASP headers: {}", it)
            }
        }

        /**
         * Returns a [List] of [OWASPHeader]s from [OWASP_HEADERS_ADD_URL].
         */
        private fun getHeadersToAdd(): List<OWASPHeader> {
            return restTemplate.getForObject(OWASP_HEADERS_ADD_URL, OWASPAddResponse::class.java)?.headers
                ?: listOf<OWASPHeader>().also {
                    logger.error("Could not fetch target secure headers from OWASP, some features of this library may not work as expected")
                }
        }

        /**
         * Returns a [List] of [OWASPHeader]s from [OWASP_HEADERS_REMOVE_URL]
         */
        private fun getHeadersToRemove(): List<OWASPHeader> {
            return restTemplate.getForObject(OWASP_HEADERS_REMOVE_URL, OWASPDeleteResponse::class.java)?.headers?.map {
                OWASPHeader(it, null)
            } ?: listOf<OWASPHeader>().also {
                logger.error("Could not fetch target insecure headers from OWASP, some features of this library may not work as expected")
            }
        }

        data class OWASPAddResponse(
            var lastUpdateUtc: String?,
            var headers: List<OWASPHeader>?
        )

        data class OWASPDeleteResponse(
            var lastUpdateUtc: String?,
            var headers: List<String>?
        )

        private companion object {
            /** OWASP URL used to fetch the HTTP Secure Headers that need to be added. */
            private const val OWASP_HEADERS_ADD_URL = "https://owasp.org/www-project-secure-headers/ci/headers_add.json"
            /** OWASP URL used to fetch the HTTP Headers that pose a security risk. */
            private const val OWASP_HEADERS_REMOVE_URL =
                "https://owasp.org/www-project-secure-headers/ci/headers_remove.json"
        }
    }
}
