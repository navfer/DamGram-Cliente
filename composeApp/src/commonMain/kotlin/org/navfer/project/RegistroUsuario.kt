package org.navfer.project
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.window.AwtWindow
import androidx.navigation.NavHostController
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.io.FilenameFilter
import java.util.Base64

@Composable
fun RegistroUsuario(navController: NavHostController, modifier: Modifier = Modifier,registerFunction:((String,String,String) -> Boolean)? = null){
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var avatarRuta by remember { mutableStateOf("") }
    var avatarBase64 by remember { mutableStateOf<String?>(null) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isImageDialogOpen by remember { mutableStateOf(false) }
    var enabledButton = usuario.isNotBlank() && password.isNotBlank()
    var showError by remember { mutableStateOf(false) }


    fun convertirBase64(imageFile: File): String {
        val bytes = imageFile.readBytes()
        return Base64.getEncoder().encodeToString(bytes)
    }
    if (isImageDialogOpen) {
        AvatarChooserRegistro { selectedFile ->
            isImageDialogOpen = false
            if (selectedFile != null) {
                avatarRuta = selectedFile.absolutePath
                avatarBase64 = convertirBase64(selectedFile)
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "N U E V O  U S U A R I O",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Gray
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Column {
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
                }

                //vista previa del avatar
                if (avatarRuta.isNotEmpty()) {
                    //Spacer(modifier = Modifier.height(8.dp))
                    MyAvatar(
                        ruta = avatarRuta,
                        modifier = Modifier.size(150.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                ),
                onClick = { isImageDialogOpen = true }) {
                Text("Seleccionar Avatar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.padding(2.dp)) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    ),
                    onClick = {
                        if (registerFunction != null) {
                            val success = registerFunction(usuario, password,
                                avatarBase64.toString()
                            )
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

@Composable
fun MyAvatar(ruta: String,modifier: Modifier = Modifier) {
    val file = File(ruta)
    val imageBitmap = file.inputStream().buffered().use(::loadImageBitmap)

    Image(
        painter = BitmapPainter(imageBitmap),
        contentDescription = "Avatar",
        modifier = modifier
            .size(150.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun AvatarChooserRegistro(onClose: (File?) -> Unit) {
    AwtWindow(
        create = {
            object : FileDialog(null as Frame?, "Selecciona una imagen JPEG", FileDialog.LOAD) {
                init {
                    directory = System.getProperty("user.home")
                    this.filenameFilter = FilenameFilter { _, name ->
                        name.endsWith(".jpg", ignoreCase = true) || name.endsWith(".jpeg", ignoreCase = true)
                    }
                }

                override fun setVisible(value: Boolean) {
                    super.setVisible(value)
                    if (value) {
                        onClose(file?.let { File(directory, it) })
                    }
                }
            }
        },
        dispose = FileDialog::dispose
    )
}