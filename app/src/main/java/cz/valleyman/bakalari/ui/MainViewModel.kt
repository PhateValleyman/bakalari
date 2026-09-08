package cz.valleyman.bakalari.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.valleyman.bakalari.sync.SyncManager
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    
    private val syncManager = SyncManager(application)

    init {
        // Automatically schedule sync when ViewModel is created
        syncManager.schedulePeriodicSync()
    }

    fun refresh() {
        viewModelScope.launch {
            // In a real app, this would trigger an immediate sync and update UI state
            syncManager.startImmediateSync()
        }
    }
}
