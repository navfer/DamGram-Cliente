package org.navfer.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.AwtWindow
import org.navfer.project.post.Post
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.io.FilenameFilter
import java.util.Base64

/**
 * Aquí se crean los nuevos Posts.
 * vm -> AppViewModel
 */
@Composable
fun NewMain(vm: AppViewModel, modifier: Modifier = Modifier) {
    var imagenRuta by remember { mutableStateOf("") }
    var imagenBase64 by remember { mutableStateOf<String?>(null) }
    var info by remember { mutableStateOf("") }
    var isImageDialogOpen by remember { mutableStateOf(false) }
    val enabledButton by remember(info) { mutableStateOf(info.isNotBlank() && info.length <= 200) }  //reactivo solo se puede enviar publicacion si cumple las condiciones
    var mensaje by remember { mutableStateOf("") }

    //Convierte a Base64 la imagen
    fun convertirBase64(imageFile: File): String {
        val bytes = imageFile.readBytes()
        return Base64.getEncoder().encodeToString(bytes)
    }

    if (isImageDialogOpen) {
        ImageChooser(
            onClose = { selectedFile ->
                isImageDialogOpen = false
                if (selectedFile != null) {
                    imagenRuta = selectedFile.absolutePath
                    imagenBase64 = convertirBase64(selectedFile)
                }
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "N u e v o  P o s t",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                ),
                onClick = { isImageDialogOpen = true }
            ) {
                Text("Agregar imagen")
            }
            if (imagenRuta.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                MyImage(ruta = imagenRuta)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = info,
                onValueChange = { info = it },
                label = { Text("Info") },
                placeholder = { Text("Aquí tu texto") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                ),
                onClick = {
                    // Crear un nuevo Post
                    val nuevoPost = Post(
                        userId = vm.user.value!!.id!!,
                        image = imagenBase64,//imagen ya pasada a base64
                        info = info,
                        timestamp = System.currentTimeMillis(),
                        comments = emptyList(),
                        likes = emptyList()
                    )
                    //llamada al viewModel
                    val resultado = vm.subirPost(post = nuevoPost)
                    if(resultado){
                        mensaje = "Se ha subido el post correctamente :)"
                        imagenRuta = ""
                        imagenBase64 = null
                        info = ""
                        isImageDialogOpen = false

                    }else{
                        mensaje = "Ha ocurrido un error al subir el post :("
                    }
                },
                enabled = enabledButton,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Publicar")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = mensaje,
                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun MyImage(ruta: String) {
    val file = File(ruta)
    val imageBitmap = file.inputStream().buffered().use(::loadImageBitmap)

    Image(
        painter = BitmapPainter(imageBitmap),
        contentDescription = "Imagen seleccionada",
        contentScale = ContentScale.Fit,
        modifier = Modifier.width(300.dp).padding(16.dp)
    )
}

@Composable
fun ImageChooser(onClose: (File?) -> Unit) {
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