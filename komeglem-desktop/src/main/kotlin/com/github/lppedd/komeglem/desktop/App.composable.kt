package com.github.lppedd.komeglem.desktop

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*

/**
 * @author Edoardo Luppi
 */
@Preview
@Composable
fun App() {
  var text by remember {
    mutableStateOf("komeglem")
  }

  MaterialTheme {
    Button(onClick = { text = "komeglem, clicked" }) {
      Text(text)
    }
  }
}
