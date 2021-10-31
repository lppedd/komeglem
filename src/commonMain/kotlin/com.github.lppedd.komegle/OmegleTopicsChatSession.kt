package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
interface OmegleTopicsChatSession : OmegleChatSession {
  fun addListener(listener: OmegleTopicsChatListener)
  fun isConnected(): Boolean
}
