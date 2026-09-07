package cz.valleyman.bakalari.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homework")
data class HomeworkEntity(
    @PrimaryKey val id: String,
    val subject: String,
    val text: String,
    val dueDate: String?,
    val completed: Boolean = false
)
