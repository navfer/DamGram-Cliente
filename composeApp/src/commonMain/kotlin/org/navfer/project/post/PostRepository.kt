package org.navfer.project.post

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
import org.navfer.project.user.LogIn
import org.navfer.project.user.UserCreado

class PostRepository {
    val client = HttpClient() {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting() // ✅ Formatea JSON legible
                serializeNulls()
            }
        }
    }

    suspend fun addComment(postId: String, userId: String, comentario: String): Boolean{
        val response: HttpResponse = client.post("http://localhost:8080/posts/$postId/comment/$userId"){
            contentType(ContentType.Application.Json)
            setBody(comentario)
        }
        if (response.status == HttpStatusCode.OK) {
            return true
        } else {
            return false
        }
    }

    suspend fun addLike(postId: String, userId: String):Boolean{
        val response: HttpResponse = client.post("http://localhost:8080/posts/$postId/like/$userId"){
            contentType(ContentType.Application.Json)
        }
        if (response.status == HttpStatusCode.OK) {
            return true
        } else {
            return false
        }
    }

    suspend fun crearNuevoPost(post: Post): Boolean {
        return try {
            val response: HttpResponse = client.post("http://localhost:8080/posts") {
                contentType(ContentType.Application.Json)
                setBody(post)
            }
            response.status == HttpStatusCode.Created
        } catch (e: Exception) {
            false
        }
    }



    suspend fun getAllPosts(): List<Post>? {
        return try {
            val response: HttpResponse = client.get("http://localhost:8080/posts")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error al obtener posts: ${e.message}")
            null
        }
    }

    suspend fun getPostID(postId: String): Post?{
        return try {
            val response: HttpResponse = client.get("http://localhost:8080/posts/$postId")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error al obtener el Post: ${e.message}")
            null
        }
    }

    suspend fun getPostUsuario(userId: String): List<Post>? {
        return try {
            val response: HttpResponse = client.get("http://localhost:8080/posts/user/$userId")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error al obtener los post del usuario: ${e.message}")
            null
        }
    }

}