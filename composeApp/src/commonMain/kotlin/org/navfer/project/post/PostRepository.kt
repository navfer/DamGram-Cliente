package org.navfer.project.post

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson

class PostRepository {
    val client = HttpClient() {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting() // ✅ Formatea JSON legible
                serializeNulls()
            }
        }
    }


    /*
    suspend fun getAllPosts(): List<Post> {
        try{
            val lista = client.get("http://localhost:8080/posts")
            return lista
        }catch (e: Exception){
            return emptyList()
        }
    }

     */
}