package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface OmegleTopicsChatSession : OmegleChatSession {
  /**
   * Connects the chat session to a stranger.
   */
  public suspend fun connect(language: String, topic: String, vararg otherTopics: String)
}
