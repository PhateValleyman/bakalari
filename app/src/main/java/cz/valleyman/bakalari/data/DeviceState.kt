package cz.valleyman.bakalari.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Synchronised state between parent and child devices.
 */
enum class DeviceLockState {
    UNLOCKED,
    HOMEWORK_REQUIRED,
    WAITING_PARENT,
    APPROVED
}

@Entity(tableName = "device_state")
data class DeviceState(
    @PrimaryKey val deviceId: String,
    val lockState: DeviceLockState,
    val lastSync: Long
)
