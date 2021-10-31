package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
class OmegleApiException(message: String) : Exception(message) {
  override val message: String
    get() = super.message!!
}
