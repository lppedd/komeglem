package com.github.lppedd.komegle

import com.github.lppedd.komegle.OmegleExecutor.ScheduledTask
import kotlin.time.DurationUnit

/**
 * @author Edoardo Luppi
 */
actual class DefaultOmegleExecutor : OmegleExecutor {
  actual override fun submit(task: () -> Unit) {
    TODO("Not yet implemented")
  }

  actual override fun schedule(task: () -> Unit, delay: Long, delayUnit: DurationUnit): ScheduledTask {
    TODO("Not yet implemented")
  }

  actual override fun dispose() {
    TODO("Not yet implemented")
  }
}
