package org.navfer.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import org.navfer.project.descubrir.DescubrirMain

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    HOME("Home", Icons.Default.Home, "Home"),
    DESCUBRIR("Descubrir", Icons.Filled.Person, "Descubrir"),
    NEW("Nuevo", Icons.Filled.AddCircle, "Nuevo"),
    PERFIL("Mi perfil", Icons.Default.AccountCircle, "Perfil"),
    SALIR("Salir", Icons.Filled.Logout, "Salir")
}

@Composable
fun Home(vm: AppViewModel,modifier: Modifier = Modifier, salir: () -> Unit) {
    var selected by remember { mutableStateOf(AppDestinations.HOME) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                AppDestinations.entries.forEach { destination ->
                    NavigationBarItem(
                        icon = { Icon(destination.icon, contentDescription = destination.contentDescription) },
                        label = { Text(destination.label) },
                        selected = selected == destination,
                        onClick = {
                            if (destination == AppDestinations.SALIR) salir()
                            else selected = destination
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                when (selected) {
                    AppDestinations.HOME -> Bienvenida()
                    AppDestinations.DESCUBRIR -> DescubrirMain(vm)
                    AppDestinations.NEW -> NewMain(vm)
                    AppDestinations.PERFIL -> PerfilMain(vm)
                    AppDestinations.SALIR -> {}
                }
            }
        }
    }
}
