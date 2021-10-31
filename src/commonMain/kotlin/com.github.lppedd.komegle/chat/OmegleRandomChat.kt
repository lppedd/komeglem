package com.github.lppedd.komegle.chat

import com.github.lppedd.komegle.OmegleApi
import com.github.lppedd.komegle.OmegleChat
import com.github.lppedd.komegle.event.OmegleEventDispatcher

/**
 * @author Edoardo Luppi
 */
internal open class OmegleRandomChat(
  private val api: OmegleApi,
  private val clientId: String,
  private val dispatcher: OmegleEventDispatcher,
) : OmegleChat {
  override fun getClientId(): String =
    clientId

  override fun sendMessage(message: String) {
    api.sendMessage(clientId, message)
  }

  override fun startTyping() {
    api.startTyping(clientId)
  }

  override fun stopTyping() {
    api.stopTyping(clientId)
  }

  override fun disconnect() {
    api.disconnect(clientId)
    dispatcher.stop()
  }
}
