package org.navfer.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import damgram.composeapp.generated.resources.Res
import damgram.composeapp.generated.resources.compose_multiplatform
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(vm: AppViewModel=  koinViewModel<AppViewModel>()) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login" // Pantalla inicial
    ) {
        composable("login") {
            LogIn(vm,navController) { email, password ->
                val resultado = vm.validate(email, password)
                resultado
            }
        }
        composable("home") {
            Home(vm, salir = {
                navController.navigate("login")
            })
        }

        composable("registro") {
            RegistroUsuario(navController){ username, password, avatar ->
                val resultado = vm.createUser(username, password,avatar)
                resultado
            }
        }

    }

}