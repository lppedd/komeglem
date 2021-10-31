package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
interface OmegleApi {
  /** Returns the current Omegle status information. */
  @Throws(OmegleApiException::class)
  fun status(): OmegleStatus

  /**
   * Initializes a chat with a random partner.
   *
   * @param topics optional topics to match with the partner
   */
  @Throws(OmegleApiException::class)
  fun connect(vararg topics: String): OmegleConnection

  /**
   * Fetches events for an active chat.
   *
   * @param clientId the client ID of the active chat
   */
  @Throws(OmegleApiException::class)
  fun events(clientId: String): Any

  /**
   * Signals we have started typing a message.
   *
   * @param clientId the client ID of the active chat
   */
  @Throws(OmegleApiException::class)
  fun startTyping(clientId: String)

  /**
   * Signals we have stopped typing a message.
   *
   * @param clientId the client ID of the active chat
   */
  @Throws(OmegleApiException::class)
  fun stopTyping(clientId: String)

  /**
   * Sends a message to the other user.
   *
   * @param clientId the client ID of the active chat
   * @param message the message to send
   */
  @Throws(OmegleApiException::class)
  fun sendMessage(clientId: String, message: String)

  /**
   * Asks Omegle to stop looking for a partner with common interests,
   * and instead select a random one.
   *
   * @param clientId the client ID of the active chat
   */
  @Throws(OmegleApiException::class)
  fun stopLookingForCommonLikes(clientId: String)

  /**
   * Disconnects a chat.
   *
   * @param clientId the client ID of the active chat
   */
  @Throws(OmegleApiException::class)
  fun disconnect(clientId: String)
}
