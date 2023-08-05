package io.perera.spring.secure.annotation

import io.perera.spring.secure.config.SpringSecureAutoConfiguration
import org.springframework.context.annotation.Import

/**
 * Enable the Spring Secure Library.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Import(SpringSecureAutoConfiguration::class)
annotation class EnableSpringSecure
