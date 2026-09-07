package cz.valleyman.bakalari.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commands")
data class CommandEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val command: String,
    val createdAt: Long,
    val processed: Boolean = false
)
