package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
expect class DefaultOmegleApi() : OmegleApi {
  override fun status(): OmegleStatus
  override fun connect(vararg topics: String): OmegleConnection
  override fun events(clientId: String): List<OmegleEvent>
  override fun startTyping(clientId: String)
  override fun stopTyping(clientId: String)
  override fun sendMessage(clientId: String, message: String)
  override fun stopLookingForCommonLikes(clientId: String)
  override fun disconnect(clientId: String)
}
