package cz.valleyman.bakalari.bakalari

/**
 * Client abstraction for Bakalari API.
 * Implementation will follow after API authentication flow is verified.
 */
class BakalariClient(
    private val baseUrl: String
) {
    fun login(username: String, password: String): Boolean {
        return false
    }

    fun getHomework(): List<Any> {
        return emptyList()
    }
}
