package com.github.lppedd.komeglem.ext

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement

/**
 * @author Edoardo Luppi
 * @suppress
 */
@Suppress("NOTHING_TO_INLINE")
internal inline fun JsonArray.getStringList(): List<String> =
  map(JsonElement::str)
