package org.navfer.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.compose.KoinApplication

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DamGram",
    ) {
        KoinApplication(application = {
            modules(appModule)

        }) {
            App()
        }
    }
}