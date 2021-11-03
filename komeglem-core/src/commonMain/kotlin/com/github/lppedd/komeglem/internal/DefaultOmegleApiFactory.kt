package com.github.lppedd.komeglem.internal

import com.github.lppedd.komeglem.OmegleApi
import com.github.lppedd.komeglem.OmegleApiFactory
import com.github.lppedd.komeglem.OmegleStatus

/**
 * @author Edoardo Luppi
 */
internal class DefaultOmegleApiFactory : OmegleApiFactory {
  override suspend fun create(): OmegleApi {
    val status = DefaultOmegleApi.status()
    val chatServer = getChatServer(status)
    val antiNudeServer = getAntiNudeServer(status)
    return DefaultOmegleApi(chatServer, antiNudeServer)
  }

  private fun getChatServer(status: OmegleStatus): String {
    val serverName = status.servers.random()
    return "https://$serverName.omegle.com"
  }

  private fun getAntiNudeServer(status: OmegleStatus): String {
    val serverUrl = status.antiNudeServers.random()
    return "https://$serverUrl"
  }
}
