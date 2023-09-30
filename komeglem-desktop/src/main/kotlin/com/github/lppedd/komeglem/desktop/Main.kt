package com.github.lppedd.komeglem.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * @author Edoardo Luppi
 */
fun main() = application {
  Window(onCloseRequest = ::exitApplication) {
    App()
  }
}
