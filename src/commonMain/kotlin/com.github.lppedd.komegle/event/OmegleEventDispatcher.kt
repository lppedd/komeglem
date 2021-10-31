package com.github.lppedd.komegle.event

import com.github.lppedd.komegle.OmegleApi
import com.github.lppedd.komegle.OmegleEvent
import com.github.lppedd.komegle.OmegleExecutor

/**
 * An internal object to fetch and dispatch events sent by Omegle.
 *
 * @author Edoardo Luppi
 */
internal expect class OmegleEventDispatcher(
  executor: OmegleExecutor,
  api: OmegleApi,
  clientId: String,
) {
  /** Adds a listener that will be notified when new events are fetched from Omegle. */
  fun addListener(listener: OmegleEventListener)

  /** Starts fetching events from Omegle. */
  fun start()

  /** Stops fetching events from Omegle. */
  fun stop()

  /** Dispatches Omegle events to registered listeners. */
  fun dispatchEvents(events: List<OmegleEvent>)
}
