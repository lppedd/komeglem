package com.github.lppedd.komeglem.ext

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive

/**
 * @author Edoardo Luppi
 * @suppress
 */
internal val JsonElement.str: String
  inline get() = jsonPrimitive.content
