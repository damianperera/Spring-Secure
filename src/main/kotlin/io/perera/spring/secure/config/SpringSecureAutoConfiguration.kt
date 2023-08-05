package io.perera.spring.secure.config

import io.perera.spring.secure.core.ResponseInterceptor
import io.perera.spring.secure.annotation.EnableSpringSecure
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Auto Configuration specification for the [EnableSpringSecure] annotation.
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(RestTemplate::class)
class SpringSecureAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun secureRestTemplateInterceptor(): HandlerInterceptor = ResponseInterceptor()

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
