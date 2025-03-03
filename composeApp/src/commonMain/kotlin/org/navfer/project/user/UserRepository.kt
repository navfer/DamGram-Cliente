package org.navfer.project.user

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import kotlinx.serialization.Serializable

@Serializable
data class LogIn(val username: String, val password: String)

@Serializable
data class UserCreado(val username: String, val password: String, val avatar: String?)

class UserRepository {

    val client = HttpClient() {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting() // ✅ Formatea JSON legible
                serializeNulls()
            }
        }
    }

    suspend fun login(user:String,password:String): Boolean {
        val response: HttpResponse =
            client.post("http://localhost:8080/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LogIn(user, password))
            }
        return response.status== HttpStatusCode.OK
    }

    suspend fun postUser(username: String,password: String,avatar:String): Boolean{
        val response: HttpResponse =
            client.post("http://localhost:8080/user") {
                contentType(ContentType.Application.Json)
                setBody(UserCreado(username= username,password = password,avatar = avatar))
            }
        return response.status== HttpStatusCode.Created
    }

    suspend fun getUser(username: String): UserSerializable? {
        return try {
            val response: HttpResponse = client.get("http://localhost:8080/users/username/$username")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error al obtener el usuario: ${e.message}")
            null
        }
    }



}