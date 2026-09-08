package cz.valleyman.bakalari.security

import kotlinx.serialization.Serializable

@Serializable
data class SignedPayload(
    val command: String,
    val timestamp: Long,
    val deviceId: String,
    val signature: String
)
