package com.github.lppedd.komeglem.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual fun Dispatchers.forEventLoop(): CoroutineDispatcher =
  Main.immediate
