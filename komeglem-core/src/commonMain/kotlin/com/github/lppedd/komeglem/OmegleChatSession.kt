package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface OmegleChatSession {
  /**
   * Returns whether the chat session is connected to a stranger.
   */
  public fun isConnected(): Boolean

  /**
   * Disconnects the chat session from the stranger.
   */
  public suspend fun disconnect()
}
