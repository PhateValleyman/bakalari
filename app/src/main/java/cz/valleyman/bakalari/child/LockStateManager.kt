package cz.valleyman.bakalari.child

import cz.valleyman.bakalari.data.DeviceState

class LockStateManager {
    private var state = DeviceState.UNLOCKED

    fun getState(): DeviceState = state

    fun updateState(newState: DeviceState) {
        state = newState
    }
}
