package com.github.lppedd.komegle

import com.github.lppedd.komegle.OmegleExecutor.ScheduledTask
import java.util.concurrent.Executors
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toTimeUnit

/**
 * @author Edoardo Luppi
 */
actual class DefaultOmegleExecutor : OmegleExecutor {
  private val scheduledExecutor = Executors.newScheduledThreadPool(3)
  private val executor = Executors.newCachedThreadPool()

  actual override fun submit(task: () -> Unit) {
    executor.submit(task)
  }

  @ExperimentalTime
  actual override fun schedule(task: () -> Unit, delay: Long, delayUnit: DurationUnit): ScheduledTask {
    val future = scheduledExecutor.scheduleWithFixedDelay(task, delay, delay, delayUnit.toTimeUnit())
    return object : ScheduledTask {
      override fun cancel() {
        future.cancel(true)
      }
    }
  }

  actual override fun dispose() {
    scheduledExecutor.shutdown()
    executor.shutdown()
  }
}
