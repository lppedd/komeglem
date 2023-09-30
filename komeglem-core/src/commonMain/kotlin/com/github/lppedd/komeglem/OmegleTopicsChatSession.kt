package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface OmegleTopicsChatSession : OmegleChatSession {
  /**
   * Connects the chat session to a stranger.
   *
   * @param language A two-chars language code representing the preferred chat language, e.g., `en`, `it`
   * @param topic The first of the topics that must be common with the stranger
   * @param otherTopics Additional topics
   */
  public suspend fun connect(language: String, topic: String, vararg otherTopics: String)
}
