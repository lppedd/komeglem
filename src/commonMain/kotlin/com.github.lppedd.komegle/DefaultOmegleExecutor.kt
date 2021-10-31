package com.github.lppedd.komegle

import com.github.lppedd.komegle.OmegleExecutor.ScheduledTask
import kotlin.time.DurationUnit

/**
 * @author Edoardo Luppi
 */
expect class DefaultOmegleExecutor() : OmegleExecutor {
  override fun submit(task: () -> Unit)
  override fun schedule(task: () -> Unit, delay: Long, delayUnit: DurationUnit): ScheduledTask
  override fun dispose()
}
