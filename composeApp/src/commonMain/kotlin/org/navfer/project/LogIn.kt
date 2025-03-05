package org.navfer.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LogIn(viewModel: AppViewModel,navController: NavHostController, modifier: Modifier = Modifier, loginFunction: ((String, String) -> Boolean)? =null) {
    var username by remember { mutableStateOf("costa") }
    var password by remember { mutableStateOf("1234") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var enabledButton =username.isNotBlank() && password.isNotBlank()
    var showError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Gray
            )


            Text(
                text = "I n i c i a r  S e s i ó n",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Gray
            )

            Text(
                text = "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Campo de usuario
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                placeholder = { Text("Introduce  usuario") },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Campo de contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                placeholder = { Text("Introduce contraseña") },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        Icon(icon, contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña")
                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .padding(2.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    ),
                    onClick = {
                        if (loginFunction != null) {
                            val success = loginFunction(username, password)
                            if (success) {
                                viewModel.inicializarUsuario(username)
                                //viewModel.inicializarPosts()
                                navController.navigate("home")
                            } else {
                                showError = true
                            }
                        }
                    },
                    enabled = enabledButton,


                ) {
                    Text("Iniciar Sesión")
                }

                Spacer(modifier = Modifier.height(20.dp))


                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    ),
                        onClick = {
                            navController.navigate("registro")
                        }
                        ){
                    Text(text = "¿Nuevo usuario?")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje de error
            if (showError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Campos incorrectos. ¿Has olvidado tu contraseña?",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}