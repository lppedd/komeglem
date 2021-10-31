package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
interface OmegleChatBaseListener<T : OmegleChat> {
  fun onWaiting(chat: T) {}
  fun onIdentDigests(chat: T, digests: List<String>) {}
  fun onConnected(chat: T) {}
  fun onDisconnected(chat: T) {}
  fun onServerMessage(chat: T, message: String) {}
  fun onMessage(chat: T, message: String) {}
  fun onStartedTyping(chat: T) {}
  fun onStoppedTyping(chat: T) {}
  fun onReCaptchaRequired(chat: T, reCaptchaId: String) {}
  fun onReCaptchaRejected(chat: T) {}
}
