package com.github.lppedd.komeglem

/**
 * Exposes the Omegle public API.
 *
 * @author Edoardo Luppi
 */
public interface OmegleApi {
  /**
   * Returns the current Omegle status information.
   */
  public suspend fun status(randomId: String): OmegleStatus

  /**
   * Connects with a stranger.
   *
   * @param options The connection options
   */
  public suspend fun connect(options: OmegleConnectOptions): OmegleConnection

  /**
   * Fetches events for an active chat.
   *
   * @param clientId The client ID of the active chat
   */
  public suspend fun events(clientId: String): List<OmegleEvent>

  /**
   * Signals we have started typing a message.
   *
   * @param clientId The client ID of the active chat
   */
  public suspend fun startTyping(clientId: String)

  /**
   * Signals we have stopped typing a message.
   *
   * @param clientId The client ID of the active chat
   */
  public suspend fun stopTyping(clientId: String)

  /**
   * Sends a message to the stranger.
   *
   * @param clientId The client ID of the active chat
   * @param message The message to send
   */
  public suspend fun sendMessage(clientId: String, message: String)

  /**
   * Asks Omegle to stop looking for a stranger with common interests,
   * and instead select a random one.
   *
   * @param clientId The client ID of the active chat
   */
  public suspend fun stopLookingForCommonLikes(clientId: String)

  /**
   * Disconnects a chat.
   *
   * @param clientId The client ID of the active chat
   */
  public suspend fun disconnect(clientId: String)
}
