package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public data class OmegleConnection(
  /**
   * A client-generated random string containing `2-9` and `A-Z`, but excluding `I` and `O`.
   */
  val randomId: String,

  /**
   * A unique ID assigned to the client by the server.
   */
  val clientId: String,

  /**
   * The initial list of events returned by the server immediately after connecting.
   */
  val events: List<OmegleEvent>,
)
