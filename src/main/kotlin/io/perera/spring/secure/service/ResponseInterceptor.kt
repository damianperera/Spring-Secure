package io.perera.spring.secure.service

import io.perera.spring.secure.domain.TargetHeaders
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component

@Component
class ResponseInterceptor(
    private val targetHeaders: TargetHeaders
): ClientHttpRequestInterceptor {
    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        return execution.execute(request, body).also {
            it.headers.addHeaders()
            it.headers.removeHeaders()
        }
    }

    private fun HttpHeaders.addHeaders() = targetHeaders.headersToAdd.forEach {
        this.addIfAbsent(it.name, it.value)
    }

    private fun HttpHeaders.removeHeaders() = targetHeaders.headersToRemove.forEach {
        this.remove(it.name)
    }
}