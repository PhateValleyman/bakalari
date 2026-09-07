package cz.valleyman.bakalari.sync

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        // Homework synchronization will be implemented here
        return Result.success()
    }
}
