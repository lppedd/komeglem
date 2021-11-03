@file:JvmName("OmegleFactory")

package com.github.lppedd.komeglem

import com.github.lppedd.komeglem.internal.DefaultOmegle
import com.github.lppedd.komeglem.internal.DefaultOmegleApiFactory
import kotlin.jvm.JvmName

/**
 * @author Edoardo Luppi
 */
@JvmName("create")
public fun omegle(apiFactory: OmegleApiFactory? = null): Omegle =
  DefaultOmegle(apiFactory ?: DefaultOmegleApiFactory())
