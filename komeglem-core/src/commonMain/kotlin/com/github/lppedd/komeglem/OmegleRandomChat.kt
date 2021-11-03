package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface OmegleRandomChat {
  public fun getClientId(): String
  public fun isConnected(): Boolean
  public suspend fun status(): OmegleStatus
  public suspend fun sendMessage(message: String)
  public suspend fun startTyping()
  public suspend fun stopTyping()
  public suspend fun disconnect()
}
