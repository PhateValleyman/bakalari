package cz.valleyman.bakalari.child

import cz.valleyman.bakalari.data.DeviceLockState

class LockStateManager {
    private var state = DeviceLockState.UNLOCKED

    fun getState(): DeviceLockState = state

    fun updateState(newState: DeviceLockState) {
        state = newState
    }
}
