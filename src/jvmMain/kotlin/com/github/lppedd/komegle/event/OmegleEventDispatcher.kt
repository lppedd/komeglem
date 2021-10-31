package com.github.lppedd.komegle.event

import com.github.lppedd.komegle.OmegleApi
import com.github.lppedd.komegle.OmegleExecutor
import com.github.lppedd.komegle.OmegleExecutor.ScheduledTask
import kong.unirest.json.JSONArray
import kotlin.time.DurationUnit

/**
 * @author Edoardo Luppi
 */
internal actual class OmegleEventDispatcher actual constructor(
  private val executor: OmegleExecutor,
  private val api: OmegleApi,
  private val clientId: String,
) {
  private val listeners = LinkedHashSet<OmegleEventListener>(4, 1F)
  private var eventsTask: ScheduledTask? = null

  actual fun addListener(listener: OmegleEventListener) {
    listeners.add(listener)
  }

  actual fun start() {
    eventsTask = executor.schedule(
      ::fetchAndDispatch,
      300,
      DurationUnit.MILLISECONDS
    )
  }

  actual fun stop() {
    eventsTask?.cancel()
  }

  actual fun dispatchEvents(events: Any) {
    dispatchEvents(events as JSONArray)
  }

  private fun fetchAndDispatch() {
    val events = api.events(clientId) as JSONArray
    dispatchEvents(events)
  }

  private fun dispatchEvents(events: JSONArray) {
    for (event in events) {
      if (Thread.currentThread().isInterrupted) {
        return
      }

      if (event is JSONArray) {
        val type = event.getString(0)
        val value = event.opt(1)
        dispatchEvent(type, value)
      }
    }
  }

  @Suppress("unchecked_cast")
  private fun dispatchEvent(type: String, value: Any?) {
    when (type) {
      "waiting"              -> onWaiting()
      "identDigests"         -> onIdentDigests(value as String)
      "connected"            -> onConnected()
      "serverMessage"        -> onServerMessage(value as String)
      "typing"               -> onStartedTyping()
      "stoppedTyping"        -> onStoppedTyping()
      "gotMessage"           -> onMessage(value as String)
      "commonLikes"          -> onCommonLikes(value as JSONArray)
      "strangerDisconnected" -> {
        onStrangerDisconnected()
        stop()
      }
    }
  }

  private fun onWaiting() =
    listeners.forEach { it.onWaiting() }

  private fun onIdentDigests(digest: String) {
    val digests = digest.split(",")
    listeners.forEach { it.onIdentDigests(digests) }
  }

  private fun onConnected() =
    listeners.forEach { it.onConnected() }

  private fun onServerMessage(message: String) =
    listeners.forEach { it.onServerMessage(message) }

  private fun onStartedTyping() =
    listeners.forEach { it.onStartedTyping() }

  private fun onStoppedTyping() =
    listeners.forEach { it.onStoppedTyping() }

  private fun onMessage(message: String) =
    listeners.forEach { it.onMessage(message) }

  private fun onStrangerDisconnected() =
    listeners.forEach { it.onDisconnected() }

  private fun onCommonLikes(topicsJson: JSONArray) {
    @Suppress("unchecked_cast")
    val topics = topicsJson.toList() as List<String>
    listeners.forEach { it.onCommonLikes(topics) }
  }
}
