package cz.valleyman.bakalari.ui

sealed class ScreenState {
    data object Loading : ScreenState()
    data object Unlocked : ScreenState()
    data object HomeworkRequired : ScreenState()
    data object WaitingParent : ScreenState()
    data object Error : ScreenState()
}
