package org.navfer.project
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun RegistroUsuario(navController: NavHostController, modifier: Modifier = Modifier,registerFunction:((String,String,String) -> Boolean)? = null){
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var avatar by remember{ mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var enabledButton =usuario.isNotBlank() && password.isNotBlank()
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
                text = "Registro",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Campo de usuario
            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
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
            OutlinedTextField(
                value = avatar,
                onValueChange = { avatar = it },
                label = { Text("Avatar") },
                placeholder = { Text("Introduce la ruta de tu avatar") },
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.padding(2.dp)){
                Button(
                    onClick = {
                        if (avatar.isBlank()){
                            avatar = ""
                        }
                        if (registerFunction != null) {
                            val success = registerFunction(usuario, password ,avatar)
                            if (success) {
                                navController.navigate("login")
                            } else {
                                showError = true
                            }
                        }
                    },
                    enabled = enabledButton

                ) {
                    Text("Registrarse")
                }

            }
            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje de error
            if (showError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "No se pudo registrar",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}