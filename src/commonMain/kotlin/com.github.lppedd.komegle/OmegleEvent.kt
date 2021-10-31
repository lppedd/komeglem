package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
sealed class OmegleEvent {
  /** Waiting for a stranger. */
  object Waiting : OmegleEvent()

  /** A connection to a stranger has been established. */
  object Connected : OmegleEvent()

  /** Disconnected from the stranger. No further action is possible. */
  object Disconnected : OmegleEvent()

  /** New message received from the server. */
  data class ServerMessage(val message: String) : OmegleEvent()

  /** Unknown event info. */
  data class IdentDigests(val digests: List<String>) : OmegleEvent()

  /** The topics that we share with the stranger. */
  data class CommonLikes(val topics: List<String>) : OmegleEvent()

  /** Solving a reCAPTCHA is required to connect. */
  data class ReCaptchaRequired(val id: String) : OmegleEvent()

  /** The latest reCAPTCHA has been rejected. */
  data class ReCaptchaRejected(val newId: String) : OmegleEvent()

  /** The stranger started typing. */
  object StrangerStartedTyping : OmegleEvent()

  /** The stranger stopped typing. */
  object StrangerStoppedTyping : OmegleEvent()

  /** New message received from the stranger. */
  data class StrangerMessage(val message: String) : OmegleEvent()

  /** The current Omegle status information. */
  data class StatusInfo(val status: OmegleStatus) : OmegleEvent()
}
