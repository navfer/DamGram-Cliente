package org.navfer.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.navfer.project.post.Post
import org.navfer.project.post.PostCard
import org.navfer.project.post.PostDetalle
import java.util.Base64
import java.io.ByteArrayInputStream

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun PerfilMain(viewModel: AppViewModel, modifier: Modifier = Modifier) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()
    val isListAndDetailVisible = navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded && navigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

    val user by viewModel.user.collectAsState()
    val avatarUsuario: ImageBitmap? = user?.avatar?.let {
        base64Normal(it)
    }

    val postUsuario: List<Post> = viewModel.postUsuario.value

    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .border(2.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Bienvenido, @${user!!.username}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(10.dp)
                )

                // Mostrar la imagen del avatar si existe
                avatarUsuario?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,

                    )
                }
            }
        }

    ){ innerPadding ->
        Column {
            ListDetailPaneScaffold(
                directive = navigator.scaffoldDirective,
                value = navigator.scaffoldValue,
                modifier = Modifier.padding(innerPadding),

                //lista de elementoss
                listPane = {
                    Box(
                        modifier = Modifier.padding(10.dp).then(
                            if (isListAndDetailVisible) {
                                Modifier.width(500.dp)
                            } else
                                Modifier.fillMaxSize()
                        )
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                            items(postUsuario) { post ->
                                PostCard(
                                    vm = viewModel,
                                    item = post,
                                    ver = { post ->
                                        viewModel.setSelected(post)
                                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                                    }
                                )
                            }
                        }
                    }
                },

                //vista detallada
                detailPane = {
                    val postSeleccionado = viewModel.selected.collectAsState()
                    Box(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        PostDetalle(
                            vm = viewModel,
                            item = postSeleccionado,
                            atras = {
                                if (navigator.canNavigateBack()) {
                                    navigator.navigateBack()
                                }
                            },
                            expandido = isListAndDetailVisible
                        )
                    }
                }
            )
        }

    }
}

fun base64Normal(base64: String): ImageBitmap? {
    return try {
        val decodedBytes = Base64.getDecoder().decode(base64)
        val inputStream = ByteArrayInputStream(decodedBytes)
        loadImageBitmap(inputStream)
    } catch (e: Exception) {
        null
    }
}
