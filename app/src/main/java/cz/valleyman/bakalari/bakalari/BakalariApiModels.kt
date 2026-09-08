package cz.valleyman.bakalari.bakalari

import kotlinx.serialization.Serializable

@Serializable
data class BakalariHomework(
    val ID: String,
    val Subject: String,
    val Content: String,
    val DueDate: String?
)

@Serializable
data class HomeworkResponse(
    val Homeworks: List<BakalariHomework>
)
