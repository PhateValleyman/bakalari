package cz.valleyman.bakalari.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cz.valleyman.bakalari.bakalari.BakalariClient
import cz.valleyman.bakalari.data.AppDatabase
import cz.valleyman.bakalari.data.HomeworkRepository

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val database = AppDatabase.getDatabase(applicationContext)
        val client = BakalariClient("https://zssumava.bakalari.cz") // Should be from prefs
        val repository = HomeworkRepository(database.homeworkDao(), client)

        return try {
            // TODO: In a real app, we need to handle login/session here if token expired
            repository.refreshHomework()
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}
