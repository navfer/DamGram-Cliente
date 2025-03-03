package org.navfer.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirlineSeatFlat
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Bienvenida(){

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
                text = "DamGram",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Icon(
                imageVector = Icons.Default.Cake,
                contentDescription = "Pokemon",
                tint = MaterialTheme.colorScheme.primary, // Cambia el color
                modifier = Modifier.size(96.dp)// Cambia el tamaño
            )
        }
    }
}