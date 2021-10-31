package com.github.lppedd.komegle

import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

/**
 * @author Edoardo Luppi
 */
interface OmegleExecutor {
  fun submit(task: () -> Unit)

  @ExperimentalTime
  fun schedule(task: () -> Unit, delay: Long, delayUnit: DurationUnit): ScheduledTask
  fun dispose()

  interface ScheduledTask {
    fun cancel()
  }
}
