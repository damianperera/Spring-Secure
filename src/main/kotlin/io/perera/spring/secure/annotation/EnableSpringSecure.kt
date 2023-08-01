package io.perera.spring.secure.annotation

import io.perera.spring.secure.config.SpringSecureAutoConfiguration
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Import(SpringSecureAutoConfiguration::class)
annotation class EnableSpringSecure
