package cz.valleyman.bakalari.bakalari

import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

/**
 * Client for Bakalari API.
 * Currently uses basic stubs for demonstration.
 */
class BakalariClient(
    private val baseUrl: String
) {
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }
    private var accessToken: String? = null

    suspend fun login(username: String, password: String): Boolean {
        // TODO: Implement OAuth2 or session-based login
        // For now, simulating success
        accessToken = "dummy_token"
        return true
    }

    suspend fun getHomework(): List<BakalariHomework> {
        val token = accessToken ?: return emptyList()
        
        val request = Request.Builder()
            .url("$baseUrl/api/v1/homework")
            .header("Authorization", "Bearer $token")
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return emptyList()
                val body = response.body?.string() ?: return emptyList()
                val parsed = json.decodeFromString<HomeworkResponse>(body)
                parsed.Homeworks
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
