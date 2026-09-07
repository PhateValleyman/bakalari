package cz.valleyman.bakalari.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    fun refresh() {
        viewModelScope.launch {
            // Synchronization will be implemented here
        }
    }
}
