package io.perera.spring.secure.domain

data class TargetHeaders(
    val headersToAdd: List<OWASPHeader>,
    val headersToRemove: List<OWASPHeader>
)

data class OWASPHeader (
    val name: String,
    val value: String?
)