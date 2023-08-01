package io.perera.spring.secure.config

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate

@Configuration
class SpringSecureAutoConfiguration {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var responseInterceptor: ClientHttpRequestInterceptor

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "secure", name = ["enable"], havingValue = "true")
    fun restTemplate(): RestTemplate {
        logger.debug("Could not find an existing [RestTemplate], registering a new one")
        return RestTemplate()
    }

    @PostConstruct
    @ConditionalOnProperty(prefix = "secure", name = ["enable"], havingValue = "true")
    fun registerInterceptors() {
        logger.debug("Registering response interceptors for [RestTemplate]")
        restTemplate.interceptors.add(responseInterceptor)
    }
}
