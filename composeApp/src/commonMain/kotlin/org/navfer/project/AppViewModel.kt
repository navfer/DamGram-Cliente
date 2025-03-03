package org.navfer.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.navfer.project.user.UserRepository
import org.navfer.project.user.UserSerializable
import javax.swing.text.StyledEditorKit.BoldAction

class AppViewModel (var repository: UserRepository): ViewModel( ) {
    private val _user = MutableStateFlow<UserSerializable?>(null)
    val user: StateFlow<UserSerializable?> get() = _user

    fun inicializarUsuario(username: String) {
        viewModelScope.launch {
            val userResult = repository.getUser(username)
            _user.value = userResult
        }
    }


    fun validate(name: String, password: String): Boolean {
        var resultado= runBlocking {
            repository.login(name,password)
        }
        return resultado
    }

    fun createUser(name:String,password:String,avatar:String): Boolean {
        var resultado = runBlocking {
            repository.postUser(name,password,avatar)
        }
        return resultado
    }
}