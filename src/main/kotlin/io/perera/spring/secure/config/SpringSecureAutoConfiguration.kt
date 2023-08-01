package io.perera.spring.secure.config

import io.perera.spring.secure.service.ResponseInterceptor
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(RestTemplate::class)
class SpringSecureAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun secureRestTemplateInterceptor(): HandlerInterceptor {
        return ResponseInterceptor()
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun customWebMvcConfigurer(interceptor: HandlerInterceptor): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addInterceptors(registry: InterceptorRegistry) {
                registry.addInterceptor(interceptor)
            }
        }
    }
}
