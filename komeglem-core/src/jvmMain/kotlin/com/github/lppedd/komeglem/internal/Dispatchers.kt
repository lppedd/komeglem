package com.github.lppedd.komeglem.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
internal actual fun Dispatchers.forEventLoop(): CoroutineDispatcher =
  IO.limitedParallelism(1)
