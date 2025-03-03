package org.navfer.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.navfer.project.post.Post
import org.navfer.project.post.PostRepository
import org.navfer.project.user.UserRepository
import org.navfer.project.user.UserSerializable

class AppViewModel (var userRepository: UserRepository, var postRepository: PostRepository): ViewModel( ) {
    private val _user = MutableStateFlow<UserSerializable?>(null)
    val user: StateFlow<UserSerializable?> get() = _user

    private val _postUsuario = MutableStateFlow<List<Post>>(emptyList())
    val postUsuario: StateFlow<List<Post>> get() = _postUsuario

    fun inicializarUsuario(username: String) {
        viewModelScope.launch {
            //cargamos el usuario que ha iniciado la sesion
            val userResult = userRepository.getUser(username)
            _user.value = userResult

            if (userResult != null) {
                // Obtener los posts del usuario
                val postResult = userResult.id?.let { postRepository.getPostUsuario(it) }
                if (postResult != null) {
                    _postUsuario.value = postResult
                } else {
                    _postUsuario.value = emptyList()
                }
            }
        }
    }


    fun validate(name: String, password: String): Boolean {
        var resultado= runBlocking {
            userRepository.login(name,password)
        }
        return resultado
    }

    fun createUser(name:String,password:String,avatar:String): Boolean {
        var resultado = runBlocking {
            userRepository.postUser(name,password,avatar)
        }
        return resultado
    }
}