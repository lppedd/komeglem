package com.github.lppedd.komegle

/**
 * @see OmegleApi.status
 * @author Edoardo Luppi
 */
data class OmegleStatus(
  val count: Int,
  val antiNudeServers: List<String>,
  val spyQueueTime: Double,
  val spyeeQueueTime: Double,
  val rtmfp: String,
  val antiNudePercent: Double,
  val timestamp: Double,
  val servers: List<String>,
)
