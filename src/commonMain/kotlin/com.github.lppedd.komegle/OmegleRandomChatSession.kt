package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
interface OmegleRandomChatSession : OmegleChatSession {
  fun addListener(listener: OmegleChatListener)
  fun isConnected(): Boolean
}
