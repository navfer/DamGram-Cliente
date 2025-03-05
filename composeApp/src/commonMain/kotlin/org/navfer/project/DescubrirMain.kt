package org.navfer.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import org.navfer.project.post.Post
import org.navfer.project.post.PostCard

@Composable
fun DescubrirMain(viewModel: AppViewModel, modifier: Modifier = Modifier){

    val listaPost: List<Post> = viewModel.postTotales.value

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listaPost) { post ->
            PostCard(
                item = post,
                ver = { }
            )
        }
    }
}