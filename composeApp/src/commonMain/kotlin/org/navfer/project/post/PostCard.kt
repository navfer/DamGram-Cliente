package org.navfer.project.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import java.io.ByteArrayInputStream
import java.util.Base64

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    item: Post,
    ver: (Post) -> Unit
) {
    val postImage: ImageBitmap? = item.image?.let { base64Normal(it) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(2.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), RoundedCornerShape(8.dp)) // Borde gris con esquinas redondeadas
        ) {
            Text(
                text = item.userId,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.info,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))


            postImage?.let { image ->
                Image(
                    bitmap = image,
                    contentDescription = "Imagen del post",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    //contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            //enseña la cantidad de likes y comentarios
            Row(
                horizontalArrangement = Arrangement.Start,
                //verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Likes: ${item.likes.size}",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Comentarios: ${item.comments.size}",
                    style = MaterialTheme.typography.bodySmall
                )

                //boton para entrar en la publicacion
                Button(
                    onClick = { ver(item) },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("Ver más")
                }
            }
        }
    }


// Función para convertir Base64 a ImageBitmap
fun base64Normal(base64: String): ImageBitmap? {
    return try {
        val decodedBytes = Base64.getDecoder().decode(base64)
        val inputStream = ByteArrayInputStream(decodedBytes)
        loadImageBitmap(inputStream)
    } catch (e: Exception) {
        null
    }
}
