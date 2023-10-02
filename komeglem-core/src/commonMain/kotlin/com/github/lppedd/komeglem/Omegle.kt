package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface Omegle {
  /**
   * Returns the Omegle API factory, which may be used internally to spawn new chat sessions.
   */
  public val apiFactory: OmegleApiFactory

  /**
   * Initiate a new Omegle chat with a random stranger.
   *
   * @param language A two-chars language code representing the preferred chat language
   */
  public suspend fun newRandomChat(
    listener: OmegleRandomChatListener,
    language: String,
  ): OmegleRandomChatSession

  /**
   * Initiate a new Omegle chat with a random stranger with common topics.
   *
   * @param language A two-chars language code representing the preferred chat language
   * @param topics A non-empty list of topics that must be common with the stranger
   */
  public suspend fun newTopicsChat(
    listener: OmegleTopicsChatListener,
    language: String,
    vararg topics: String,
  ): OmegleTopicsChatSession

  /**
   * Disposes the Omegle instance, freeing any allocated system resource.
   */
  public fun dispose()
}
