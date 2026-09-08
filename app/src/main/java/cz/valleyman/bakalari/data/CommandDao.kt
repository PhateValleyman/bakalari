package cz.valleyman.bakalari.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CommandDao {
    @Query("SELECT * FROM commands WHERE processed = 0 ORDER BY createdAt ASC")
    suspend fun getPendingCommands(): List<CommandEntity>

    @Insert
    suspend fun insert(command: CommandEntity)

    @Update
    suspend fun update(command: CommandEntity)

    @Query("DELETE FROM commands WHERE processed = 1")
    suspend fun deleteProcessed()
}
