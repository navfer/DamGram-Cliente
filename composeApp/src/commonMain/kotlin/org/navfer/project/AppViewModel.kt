package org.navfer.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _postTotales = MutableStateFlow<List<Post>>(emptyList())
    val postTotales: StateFlow<List<Post>> get() = _postTotales

    private val _selected = MutableStateFlow<Post?>(null)
    val selected: StateFlow<Post?> = _selected.asStateFlow()


    fun setSelected(post: Post?) {
        _selected.value = post
    }

    fun inicializarUsuario(username: String) {
        viewModelScope.launch {
            //cargamos el usuario que ha iniciado la sesion
            val userResult = userRepository.getUser(username)
            _user.value = userResult

            if (userResult != null) {
                val postResult = userResult.id?.let { postRepository.getPostUsuario(it) }
                if (postResult != null) {
                    _postUsuario.value = postResult
                } else {
                    _postUsuario.value = emptyList()
                }
            }
        }
    }

    fun inicializarPosts(){
        viewModelScope.launch {
            val posts = postRepository.getAllPosts() ?: emptyList()
            _postTotales.value = posts
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

    fun subirPost(post: Post): Boolean{
        val resultado = runBlocking {
            postRepository.crearNuevoPost(post)
        }
        if (resultado) {
            inicializarUsuario(user.value!!.username)
            inicializarPosts()
        }
        return resultado
    }

    fun buscarUsuarioId(userId: String): UserSerializable?{
        val resultado = runBlocking {
            userRepository.getUserById(userId)
        }
        return resultado
    }

    fun enviarComentario( postId:String, comentario:String): Boolean{
        val resultado = runBlocking {
            postRepository.addComment(postId, user.value!!.id!!, comentario)
        }
        if (resultado) {
            actualizar(postId)
        }

        return resultado
    }

    fun enviarLike(postId: String): Boolean{
        val resultado = runBlocking {
            postRepository.addLike(postId, user.value!!.id!!)
        }
        if (resultado) {
            actualizar(postId)
        }
        return resultado
    }

    fun actualizar(postId: String){
        viewModelScope.launch {
            _postTotales.value = postRepository.getAllPosts() ?: emptyList()
            _postUsuario.value = user.value?.id?.let { postRepository.getPostUsuario(it) } ?: emptyList()
            _selected.value = _postTotales.value.find { it.id == postId }
         }
    }

}