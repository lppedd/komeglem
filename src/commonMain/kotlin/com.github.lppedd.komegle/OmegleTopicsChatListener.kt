package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
interface OmegleTopicsChatListener : OmegleChatBaseListener<OmegleTopicsChat> {
  fun onCommonLikes(chat: OmegleTopicsChat, topics: List<String>) {}
}
