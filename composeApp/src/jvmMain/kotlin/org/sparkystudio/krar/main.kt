package org.sparkystudio.krar

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KRar Archive Utility",
    ) {
        App()
    }
}