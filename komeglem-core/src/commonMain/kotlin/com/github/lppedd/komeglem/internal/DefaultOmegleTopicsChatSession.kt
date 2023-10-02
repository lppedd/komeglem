package com.github.lppedd.komeglem.internal

import com.github.lppedd.komeglem.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlin.jvm.JvmSynthetic
import kotlin.time.Duration.Companion.seconds

/**
 * @author Edoardo Luppi
 */
internal class DefaultOmegleTopicsChatSession(
  private val omegle: Omegle,
  private val omegleChatListener: OmegleTopicsChatListener,
) : OmegleTopicsChatSession {
  private val eventLoopScope = OmegleEventLoopScope()
  private var isConnected = false
  private lateinit var omegleChat: OmegleTopicsChat

  override fun isConnected(): Boolean =
    isConnected

  @JvmSynthetic
  @Suppress("DuplicatedCode")
  internal suspend fun connect(language: String, topics: List<String>) {
    if (isConnected) {
      throw OmegleSessionException("The session is already connected")
    }

    val api = omegle.apiFactory.create()
    val connection = api.connect(OmegleConnectOptions(language, topics))

    // This chat will be sent out to listeners
    omegleChat = DefaultOmegleTopicsChat(api, connection, this)

    @OptIn(ExperimentalCoroutinesApi::class)
    val events = eventLoopScope.produce {
      // Send out the initial connection events
      for (event in connection.events) {
        dispatchEvent(event)
      }

      // Keep fetching events from the server every 500 milliseconds
      while (isActive) {
        delay(1.seconds)

        for (event in api.events(connection.clientId)) {
          send(event)
        }
      }
    }

    eventLoopScope.launch {
      for (event in events) {
        dispatchEvent(event)
      }
    }
  }

  override suspend fun disconnect() =
    onDisconnected(checkConnected = false)

  private suspend fun onConnected() {
    if (isConnected) {
      throw OmegleSessionException("The session is already connected")
    }

    isConnected = true
    omegleChatListener.onConnected(omegleChat)
  }

  private suspend fun onDisconnected(checkConnected: Boolean) {
    if (checkConnected && !isConnected) {
      throw OmegleSessionException("The session is not connected yet")
    }

    isConnected = false
    omegleChat.disconnect()
    eventLoopScope.coroutineContext.cancelChildren()
    omegleChatListener.onDisconnected()
  }

  private suspend fun dispatchEvent(event: OmegleEvent) =
    when (event) {
      OmegleEvent.Waiting               -> omegleChatListener.onWaiting(omegleChat)
      OmegleEvent.Connected             -> onConnected()
      OmegleEvent.Disconnected          -> onDisconnected(checkConnected = true)
      OmegleEvent.StrangerStartedTyping -> omegleChatListener.onStartedTyping(omegleChat)
      OmegleEvent.StrangerStoppedTyping -> omegleChatListener.onStoppedTyping(omegleChat)
      is OmegleEvent.StatusInfo         -> omegleChatListener.onStatusInfo(omegleChat, event.status)
      is OmegleEvent.IdentDigests       -> omegleChatListener.onIdentDigests(omegleChat, event.digests)
      is OmegleEvent.CommonLikes        -> omegleChatListener.onCommonLikes(omegleChat, event.topics)
      is OmegleEvent.ServerMessage      -> omegleChatListener.onServerMessage(omegleChat, event.message)
      is OmegleEvent.StrangerMessage    -> omegleChatListener.onMessage(omegleChat, event.message)
      is OmegleEvent.ReCaptchaRequired  -> omegleChatListener.onReCaptchaRequired(omegleChat, event.id)
      is OmegleEvent.ReCaptchaRejected  -> omegleChatListener.onReCaptchaRejected(omegleChat, event.newId)
      is OmegleEvent.Error              -> omegleChatListener.onError(omegleChat, event.message)
    }
}
