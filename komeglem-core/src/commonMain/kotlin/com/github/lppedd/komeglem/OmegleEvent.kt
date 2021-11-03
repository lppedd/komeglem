package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public sealed class OmegleEvent {
  /**
   * Waiting for a stranger.
   */
  public data object Waiting : OmegleEvent()

  /**
   * A connection to a stranger has been established.
   */
  public data object Connected : OmegleEvent()

  /**
   * Disconnected from the stranger. No further action is possible.
   */
  public data object Disconnected : OmegleEvent()

  /**
   * New message received from the server.
   */
  public data class ServerMessage(val message: String) : OmegleEvent()

  /**
   * Unknown event info.
   */
  public data class IdentDigests(val digests: List<String>) : OmegleEvent()

  /**
   * The topics that we share with the stranger.
   */
  public data class CommonLikes(val topics: List<String>) : OmegleEvent()

  /**
   * Solving a reCAPTCHA is required to connect.
   */
  public data class ReCaptchaRequired(val id: String) : OmegleEvent()

  /**
   * The latest reCAPTCHA has been rejected.
   */
  public data class ReCaptchaRejected(val newId: String) : OmegleEvent()

  /**
   * The stranger started typing.
   */
  public data object StrangerStartedTyping : OmegleEvent()

  /**
   * The stranger stopped typing.
   */
  public data object StrangerStoppedTyping : OmegleEvent()

  /**
   * New message received from the stranger.
   */
  public data class StrangerMessage(val message: String) : OmegleEvent()

  /**
   * The current Omegle status information.
   */
  public data class StatusInfo(val status: OmegleStatus) : OmegleEvent()

  /**
   * A generic server error.
   */
  public data class Error(val message: String) : OmegleEvent()
}
