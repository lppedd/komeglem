package com.github.lppedd.komegle.event

import com.github.lppedd.komegle.OmegleApi
import com.github.lppedd.komegle.OmegleEvent
import com.github.lppedd.komegle.OmegleExecutor

/**
 * @author Edoardo Luppi
 */
internal actual class OmegleEventDispatcher actual constructor(
  executor: OmegleExecutor,
  api: OmegleApi,
  clientId: String,
) {
  actual fun addListener(listener: OmegleEventListener) {
    TODO("Not yet implemented")
  }

  actual fun start() {
    TODO("Not yet implemented")
  }

  actual fun stop() {
    TODO("Not yet implemented")
  }

  actual fun dispatchEvents(events: List<OmegleEvent>) {
    TODO("Not yet implemented")
  }
}
