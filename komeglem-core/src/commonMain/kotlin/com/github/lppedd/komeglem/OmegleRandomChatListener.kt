package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface OmegleRandomChatListener {
  public suspend fun onWaiting(chat: OmegleRandomChat)
  public suspend fun onConnected(chat: OmegleRandomChat)
  public suspend fun onDisconnected()
  public suspend fun onIdentDigests(chat: OmegleRandomChat, digests: List<String>) {}
  public suspend fun onServerMessage(chat: OmegleRandomChat, serverMessage: String) {}
  public suspend fun onMessage(chat: OmegleRandomChat, message: String) {}
  public suspend fun onStartedTyping(chat: OmegleRandomChat) {}
  public suspend fun onStoppedTyping(chat: OmegleRandomChat) {}
  public suspend fun onReCaptchaRequired(chat: OmegleRandomChat, reCaptchaId: String) {}
  public suspend fun onReCaptchaRejected(chat: OmegleRandomChat, newReCaptchaId: String) {}
  public suspend fun onStatusInfo(chat: OmegleRandomChat, status: OmegleStatus) {}
  public suspend fun onError(chat: OmegleRandomChat, error: String) {}
}
