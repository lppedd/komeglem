package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface OmegleRandomChatSession : OmegleChatSession {
  /**
   * Connects the chat session to a stranger.
   *
   * @param language A two-chars language code representing the preferred chat language, e.g., `en`, `it`
   */
  public suspend fun connect(language: String)
}
