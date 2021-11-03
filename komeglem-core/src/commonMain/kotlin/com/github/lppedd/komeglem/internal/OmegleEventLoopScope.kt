package com.github.lppedd.komeglem.internal

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * @author Edoardo Luppi
 */
internal class OmegleEventLoopScope : CoroutineScope {
  override val coroutineContext: CoroutineContext =
    SupervisorJob() + Dispatchers.forEventLoop() + CoroutineName("omegle-event-loop")

  override fun toString(): String =
    "OmegleEventLoopScope(coroutineContext=$coroutineContext)"
}
