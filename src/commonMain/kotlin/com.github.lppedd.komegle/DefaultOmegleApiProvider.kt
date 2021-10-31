package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
class DefaultOmegleApiProvider : OmegleApiProvider {
  override fun getApi(): OmegleApi {
    return DefaultOmegleApi()
  }
}
