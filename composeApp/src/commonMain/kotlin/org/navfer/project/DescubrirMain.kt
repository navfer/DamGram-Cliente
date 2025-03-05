package org.navfer.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import org.navfer.project.post.Post
import org.navfer.project.post.PostCard
import org.navfer.project.post.PostDetalle

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DescubrirMain(viewModel: AppViewModel, modifier: Modifier = Modifier){
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()
    val isListAndDetailVisible = navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded && navigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

    val listaPost: List<Post> = viewModel.postTotales.value
    /*
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listaPost) { post ->
            PostCard(
                vm = viewModel,
                item = post,
                ver = { PostDetalle(post), },
            )
        }
    }

     */

    Column {
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            modifier = Modifier.padding(10.dp),

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
                        items(listaPost) { post ->
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