package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
class ConsoleOmegleErrorHandler : OmegleErrorHandler {
  override fun onConnectionError(message: String) {
    println("Error: $message")
  }
}
