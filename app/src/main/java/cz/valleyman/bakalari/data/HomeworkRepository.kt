package cz.valleyman.bakalari.data

import cz.valleyman.bakalari.bakalari.BakalariClient

class HomeworkRepository(
    private val homeworkDao: HomeworkDao,
    private val client: BakalariClient
) {
    suspend fun getAllHomework(): List<HomeworkEntity> {
        return homeworkDao.getAll()
    }

    suspend fun refreshHomework(): List<HomeworkEntity> {
        val remoteHomework = client.getHomework()
        val entities = remoteHomework.map { 
            // In a real app, we'd have a proper mapper here.
            // For now, assuming BakalariClient returns something that can be mapped.
            HomeworkEntity(
                id = it.hashCode().toString(), // Stub mapping
                subject = "Subject",
                text = it.toString(),
                dueDate = null,
                completed = false
            )
        }
        homeworkDao.insertAll(entities)
        return entities
    }
}
