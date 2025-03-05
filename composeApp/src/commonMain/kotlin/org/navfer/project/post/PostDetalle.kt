package org.navfer.project.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import org.navfer.project.AppViewModel
import org.navfer.project.user.UserSerializable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import damgram.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource


@Composable
fun PostDetalle(
    vm: AppViewModel,
    item: State<Post?>,
    atras: () -> Unit,
    expandido: Boolean,
){
    val post = item.value
    val postImage: ImageBitmap? = post?.image?.let { base64Normal(it) }
    val userPost: UserSerializable? = post?.let { vm.buscarUsuarioId(it.userId) }
    val comentarios: List<Comment> = post?.comments ?: emptyList()
    var comentario by remember { mutableStateOf("") }
    val enabledButton = comentario.isNotBlank()
    var mensaje by remember { mutableStateOf("") }


    if (post == null) {
        /*
        Image(
            painter = painterResource(Res.drawable.machape),
            contentDescription = "Machape",
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )

         */
        return
    }else{
        var likeDisponible by remember(post.likes) {
            mutableStateOf(!post.likes.contains(Like(vm.user.value?.id)))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .border(2.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), RoundedCornerShape(8.dp)) // Borde gris con esquinas redondeadas
                .padding(16.dp), //para lo de dentro
            //horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                //usuario que publicó el post
                Text(
                    text = "Publicación de @${userPost!!.username}",
                    style = MaterialTheme.typography.titleMedium
                )

                //boton de volver
                if (!expandido) {
                    Button(onClick = { atras() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowCircleLeft,
                            contentDescription = "Volver",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }

            Row {
                //Aqui info e imagen si tiene
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 20.dp)
                        .border(2.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                        .padding(16.dp) //para lo de dentro

                ){
                    //imagen en caso de tener
                    postImage?.let { image ->
                        Image(
                            bitmap = image,
                            contentDescription = "Imagen del post",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                        )
                    }

                    //texto del post
                    Text(
                        text = post!!.info,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    //fecha likes y comentarios totales
                    Text(
                        text = "Fecha de publicación: ${post.timestamp}\nLikes: ${post.likes.size}\nComentarios: ${post.comments.size}",
                        style = MaterialTheme.typography.bodySmall,

                        )

                }
                //Aqui esta la lista de comentarios del post y para que el usuario añada uno
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "Comentarios:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold

                    )

                    if(comentarios.size == 0){
                        Text(
                            text = "Aún no hay comentarios. Sé el primero !!"
                        )
                    }else{
                        comentarios.forEach { comment ->
                            var username: UserSerializable? = vm.buscarUsuarioId(comment.userId)
                            Text(text = "${username!!.username}: ${comment.text}")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        maxLines = 1,
                        value = comentario,
                        onValueChange = { comentario = it },
                        label = { Text("Comentario") },
                        placeholder = { Text("...") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Button(
                            enabled = likeDisponible,
                            onClick = {
                                var enviarLike = vm.enviarLike(post.id!!)
                                if(enviarLike){
                                    mensaje = "Like enviado !!"
                                }
                            }
                        ){
                            Text("Like")
                        }

                        Button(
                            enabled = enabledButton,
                            onClick = {
                                var envio = vm.enviarComentario(post.id!!,comentario)
                                if(envio){
                                    comentario = ""
                                    mensaje = "Se ha enviado el comentario correctamente ;)"
                                }
                            }
                        ){

                            Text("Enviar comentario")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row() {
                Text(
                    text = mensaje
                )
            }
        }

    }


}