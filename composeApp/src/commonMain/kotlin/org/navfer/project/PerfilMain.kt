package org.navfer.project

import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@Composable
fun PerfilMain(viewModel: AppViewModel, modifier: Modifier = Modifier) {
    val user by viewModel.user.collectAsState()
    Text(
            text = user?.toString() ?: "Usuario no disponible",
            modifier = modifier
    )
}