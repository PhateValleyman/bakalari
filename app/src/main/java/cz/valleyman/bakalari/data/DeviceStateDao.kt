package cz.valleyman.bakalari.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface DeviceStateDao {
    @Query("SELECT * FROM device_state WHERE deviceId = :deviceId")
    suspend fun getDeviceState(deviceId: String): DeviceState?

    @Upsert
    suspend fun upsert(state: DeviceState)
}
