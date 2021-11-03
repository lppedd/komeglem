package com.github.lppedd.komeglem.internal

import com.github.lppedd.komeglem.*

/**
 * @author Edoardo Luppi
 */
internal open class DefaultOmegleRandomChat(
  private val omegleApi: OmegleApi,
  private val omegleConnection: OmegleConnection,
  private val omegleChatSession: OmegleChatSession,
) : OmegleRandomChat {
  override fun getClientId(): String =
    omegleConnection.clientId

  override fun isConnected(): Boolean =
    omegleChatSession.isConnected()

  override suspend fun status(): OmegleStatus {
    requireConnected()
    return omegleApi.status(omegleConnection.randomId)
  }

  override suspend fun sendMessage(message: String) {
    requireConnected()
    omegleApi.sendMessage(omegleConnection.clientId, message)
  }

  override suspend fun startTyping() {
    requireConnected()
    omegleApi.startTyping(omegleConnection.clientId)
  }

  override suspend fun stopTyping() {
    requireConnected()
    omegleApi.stopTyping(omegleConnection.clientId)
  }

  override suspend fun disconnect() {
    if (isConnected()) {
      omegleApi.disconnect(omegleConnection.clientId)
      omegleChatSession.disconnect()
    }
  }

  protected fun requireConnected() {
    if (!isConnected()) {
      throw OmegleSessionException("The chat is not connected to a stranger")
    }
  }
}
