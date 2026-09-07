package cz.valleyman.bakalari.data

/**
 * Synchronised state between parent and child devices.
 */
enum class DeviceLockState {
    UNLOCKED,
    HOMEWORK_REQUIRED,
    WAITING_PARENT,
    APPROVED
}

data class DeviceState(
    val deviceId: String,
    val lockState: DeviceLockState,
    val lastSync: Long
)
