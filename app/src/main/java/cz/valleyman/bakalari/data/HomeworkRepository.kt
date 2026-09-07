package cz.valleyman.bakalari.data

import cz.valleyman.bakalari.bakalari.BakalariClient

class HomeworkRepository(
    private val client: BakalariClient
) {
    suspend fun refreshHomework(): List<Homework> {
        return client.getHomework().filterIsInstance<Homework>()
    }
}
