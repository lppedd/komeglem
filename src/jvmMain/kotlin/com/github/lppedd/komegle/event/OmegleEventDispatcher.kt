package com.github.lppedd.komegle.event

import com.github.lppedd.komegle.OmegleApi
import com.github.lppedd.komegle.OmegleEvent
import com.github.lppedd.komegle.OmegleEvent.*
import com.github.lppedd.komegle.OmegleExecutor
import com.github.lppedd.komegle.OmegleExecutor.ScheduledTask
import com.github.lppedd.komegle.OmegleStatus
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

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

  @ExperimentalTime
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

  actual fun dispatchEvents(events: List<OmegleEvent>) {
    for (event in events) {
      dispatchEvent(event)
    }
  }

  private fun fetchAndDispatch() {
    val events = api.events(clientId)
    dispatchEvents(events)
  }

  @Suppress("unchecked_cast")
  private fun dispatchEvent(event: OmegleEvent) {
    when (event) {
      Waiting -> onWaiting()
      Connected -> onConnected()
      is IdentDigests -> onIdentDigests(event.digests)
      is ServerMessage -> onServerMessage(event.message)
      StrangerStartedTyping -> onStartedTyping()
      StrangerStoppedTyping -> onStoppedTyping()
      is StrangerMessage -> onMessage(event.message)
      is CommonLikes -> onCommonLikes(event.topics)
      Disconnected -> onStrangerDisconnected()
      is ReCaptchaRequired -> onReCaptchaRequired(event.id)
      is ReCaptchaRejected -> onReCaptchaRejected(event.newId)
      is StatusInfo -> onStatusInfo(event.status)
    }
  }

  private fun onWaiting() =
    listeners.forEach { it.onWaiting() }

  private fun onIdentDigests(digests: List<String>) =
    listeners.forEach { it.onIdentDigests(digests) }

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

  private fun onStrangerDisconnected() {
    stop()
    listeners.forEach { it.onDisconnected() }
  }

  private fun onCommonLikes(topics: List<String>) =
    listeners.forEach { it.onCommonLikes(topics) }

  private fun onReCaptchaRequired(reCaptchaId: String) =
    listeners.forEach { it.onReCaptchaRequired(reCaptchaId) }

  private fun onReCaptchaRejected(newReCaptchaId: String) =
    listeners.forEach { it.onReCaptchaRejected(newReCaptchaId) }

  private fun onStatusInfo(status: OmegleStatus) =
    listeners.forEach { it.onStatusInfo(status) }
}
