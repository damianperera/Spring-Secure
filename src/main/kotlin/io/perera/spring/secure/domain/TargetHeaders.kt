package io.perera.spring.secure.domain

/**
 * Contains the recommended [List]s of [OWASPHeader]s to add and remove.
 */
data class TargetHeaders(
    val headersToAdd: List<OWASPHeader>,
    val headersToRemove: List<OWASPHeader>
)

/**
 * Contains a single header key-value.
 */
data class OWASPHeader (
    val name: String,
    val value: String?
)
