package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
interface OmegleChat {
  fun getClientId(): String
  fun sendMessage(message: String)
  fun startTyping()
  fun stopTyping()
  fun disconnect()
}
