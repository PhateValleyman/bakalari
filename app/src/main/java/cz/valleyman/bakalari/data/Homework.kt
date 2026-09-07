package cz.valleyman.bakalari.data

/**
 * Local representation of homework received from Bakalari.
 */
data class Homework(
    val id: String,
    val subject: String,
    val text: String,
    val dueDate: String,
    val completed: Boolean = false
)
