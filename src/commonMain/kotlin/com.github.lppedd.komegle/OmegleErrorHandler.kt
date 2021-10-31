package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
interface OmegleErrorHandler {
  fun onConnectionError(message: String)
}
