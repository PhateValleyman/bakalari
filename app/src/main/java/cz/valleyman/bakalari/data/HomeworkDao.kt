package cz.valleyman.bakalari.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface HomeworkDao {
    @Query("SELECT * FROM homework")
    suspend fun getAll(): List<HomeworkEntity>

    @Upsert
    suspend fun insertAll(items: List<HomeworkEntity>)
}
