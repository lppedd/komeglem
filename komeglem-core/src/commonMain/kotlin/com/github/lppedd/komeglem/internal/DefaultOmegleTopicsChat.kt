package com.github.lppedd.komeglem.internal

import com.github.lppedd.komeglem.OmegleApi
import com.github.lppedd.komeglem.OmegleChatSession
import com.github.lppedd.komeglem.OmegleConnection
import com.github.lppedd.komeglem.OmegleTopicsChat

/**
 * @author Edoardo Luppi
 */
internal class DefaultOmegleTopicsChat(
  private val omegleApi: OmegleApi,
  private val omegleConnection: OmegleConnection,
  omegleChatSession: OmegleChatSession,
) : DefaultOmegleRandomChat(omegleApi, omegleConnection, omegleChatSession), OmegleTopicsChat {
  override suspend fun stopLookingForCommonTopics() {
    requireConnected()
    omegleApi.stopLookingForCommonLikes(omegleConnection.clientId)
  }
}
