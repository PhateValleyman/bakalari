package cz.valleyman.bakalari.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

class Converters {
    @TypeConverter
    fun fromDeviceLockState(value: DeviceLockState): String = value.name

    @TypeConverter
    fun toDeviceLockState(value: String): DeviceLockState = DeviceLockState.valueOf(value)
}

@Database(
    entities = [
        HomeworkEntity::class,
        CommandEntity::class,
        DeviceState::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeworkDao(): HomeworkDao
    abstract fun commandDao(): CommandDao
    abstract fun deviceStateDao(): DeviceStateDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bakalari_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
