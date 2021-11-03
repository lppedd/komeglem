package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface OmegleTopicsChatListener {
  public suspend fun onWaiting(chat: OmegleTopicsChat)
  public suspend fun onConnected(chat: OmegleTopicsChat)
  public suspend fun onDisconnected()
  public suspend fun onIdentDigests(chat: OmegleTopicsChat, digests: List<String>) {}
  public suspend fun onCommonLikes(chat: OmegleTopicsChat, topics: List<String>) {}
  public suspend fun onServerMessage(chat: OmegleTopicsChat, serverMessage: String) {}
  public suspend fun onMessage(chat: OmegleTopicsChat, message: String) {}
  public suspend fun onStartedTyping(chat: OmegleTopicsChat) {}
  public suspend fun onStoppedTyping(chat: OmegleTopicsChat) {}
  public suspend fun onReCaptchaRequired(chat: OmegleTopicsChat, reCaptchaId: String) {}
  public suspend fun onReCaptchaRejected(chat: OmegleTopicsChat, newReCaptchaId: String) {}
  public suspend fun onStatusInfo(chat: OmegleTopicsChat, status: OmegleStatus) {}
  public suspend fun onError(chat: OmegleRandomChat, error: String) {}
}
