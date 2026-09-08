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
            HomeworkEntity(
                id = it.ID,
                subject = it.Subject,
                text = it.Content,
                dueDate = it.DueDate,
                completed = false
            )
        }
        if (entities.isNotEmpty()) {
            homeworkDao.insertAll(entities)
        }
        return entities
    }
}
