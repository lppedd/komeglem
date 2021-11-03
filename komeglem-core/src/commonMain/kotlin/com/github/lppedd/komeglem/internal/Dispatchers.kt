package com.github.lppedd.komeglem.internal

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * @author Edoardo Luppi
 */
internal expect fun Dispatchers.forEventLoop(): CoroutineDispatcher
