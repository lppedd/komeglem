package com.github.lppedd.komegle.chat

import com.github.lppedd.komegle.OmegleApi
import com.github.lppedd.komegle.OmegleTopicsChat
import com.github.lppedd.komegle.event.OmegleEventDispatcher

/**
 * @author Edoardo Luppi
 */
internal class OmegleTopicsRandomChat(
  private val api: OmegleApi,
  private val clientId: String,
  dispatcher: OmegleEventDispatcher,
) : OmegleRandomChat(api, clientId, dispatcher), OmegleTopicsChat {
  override fun stopLookingForCommonTopics() {
    api.stopLookingForCommonLikes(clientId)
  }
}
