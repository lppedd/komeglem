package com.github.lppedd.komeglem.ext

import kotlinx.serialization.json.*

/**
 * @author Edoardo Luppi
 * @suppress
 */
internal fun JsonObject.getInt(key: String): Int =
  this[key]!!.jsonPrimitive.int

/**
 * @author Edoardo Luppi
 * @suppress
 */
internal fun JsonObject.getDouble(key: String): Double =
  this[key]!!.jsonPrimitive.double

/**
 * @author Edoardo Luppi
 * @suppress
 */
internal fun JsonObject.getString(key: String): String =
  this[key]!!.str

/**
 * @author Edoardo Luppi
 * @suppress
 */
internal fun JsonObject.getStringList(key: String): List<String> =
  this[key]!!.jsonArray.getStringList()

/**
 * @author Edoardo Luppi
 * @suppress
 */
internal fun JsonObject.getBooleanOrElse(key: String, default: Boolean): Boolean =
  this[key]?.jsonPrimitive?.boolean ?: default
