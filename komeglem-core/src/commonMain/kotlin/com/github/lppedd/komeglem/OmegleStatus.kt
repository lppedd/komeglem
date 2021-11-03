package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 * @see OmegleApi.status
 */
public data class OmegleStatus(
  /**
   * The total active connections (probably faked).
   */
  val count: Int,

  /**
   * A `true` value indicates the IP is temporarily banned.
   */
  val forceUnmonitored: Boolean,

  /**
   * TODO
   */
  val antiNudeServers: List<String>,

  /**
   * TODO
   */
  val spyQueueTime: Double,

  /**
   * TODO
   */
  val spyeeQueueTime: Double,

  /**
   * The RTMFP endpoint for video chats.
   */
  val rtmfp: String,

  /**
   * TODO
   */
  val antiNudePercent: Double,

  /**
   * TODO
   */
  val timestamp: Double,

  /**
   * A list of available Omegle servers, usable by clients for HTTP requests.
   */
  val servers: List<String>,
)
