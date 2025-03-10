package org.navfer.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirlineSeatFlat
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Bienvenida(vm: AppViewModel){
    vm.inicializarPosts()
    //vm.user.value?.let { vm.inicializarUsuario(it.username) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            // verticalAlignment = Alignment.CenterVertically,

        ) {
            Text(
                text = "D a m  G r a m",
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(10.dp)
            )

            Icon(
                imageVector = Icons.Default.AlternateEmail,
                contentDescription = "Inicio",
                tint = Color.Gray, // cambia el color
                modifier = Modifier.size(96.dp)// cambia el tamaño
            )
        }
    }
}