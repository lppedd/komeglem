package com.github.lppedd.komegle.session

import com.github.lppedd.komegle.*
import com.github.lppedd.komegle.chat.OmegleTopicsRandomChat
import com.github.lppedd.komegle.event.OmegleEventDispatcher
import com.github.lppedd.komegle.event.OmegleEventListener

/**
 * @author Edoardo Luppi
 */
internal class DefaultOmegleRandomChatSession(private val omegle: Omegle) : OmegleRandomChatSession {
  private val listeners = LinkedHashSet<OmegleChatListener>(4, 1F)

  private var isConnected = false
  private lateinit var chat: OmegleTopicsChat

  override fun addListener(listener: OmegleChatListener) {
    listeners.add(listener)
  }

  override fun connect() {
    if (isConnected()) {
      return
    }

    try {
      connectInternal()
      isConnected = true
    } catch (e: OmegleApiException) {
      omegle.getErrorHandler().onConnectionError(e.message)
    }
  }

  override fun disconnect() {
    if (isConnected()) {
      chat.disconnect()
    }
  }

  override fun isConnected(): Boolean =
    isConnected

  private fun connectInternal() {
    val api = omegle.getApiProvider().getApi()
    val (clientId, events) = api.connect()
    val dispatcher = OmegleEventDispatcher(omegle.getExecutor(), api, clientId)
    dispatcher.addListener(RandomOmegleEventListener())
    chat = OmegleTopicsRandomChat(api, clientId, dispatcher)
    dispatcher.dispatchEvents(events)
    dispatcher.start()
  }

  private inner class RandomOmegleEventListener : OmegleEventListener {
    override fun onWaiting() {
      omegle.getExecutor().submit {
        listeners.forEach { it.onWaiting(chat) }
      }
    }

    override fun onIdentDigests(digests: List<String>) {
      omegle.getExecutor().submit {
        listeners.forEach { it.onIdentDigests(chat, digests) }
      }
    }

    override fun onCommonLikes(topics: List<String>) {
      throw UnsupportedOperationException("A random chat does not support common topics")
    }

    override fun onConnected() {
      omegle.getExecutor().submit {
        listeners.forEach { it.onConnected(chat) }
      }
    }

    override fun onDisconnected() {
      omegle.getExecutor().submit {
        listeners.forEach { it.onDisconnected(chat) }
      }
    }

    override fun onServerMessage(message: String) {
      omegle.getExecutor().submit {
        listeners.forEach { it.onServerMessage(chat, message) }
      }
    }

    override fun onMessage(message: String) {
      omegle.getExecutor().submit {
        listeners.forEach { it.onMessage(chat, message) }
      }
    }

    override fun onStartedTyping() {
      omegle.getExecutor().submit {
        listeners.forEach { it.onStartedTyping(chat) }
      }
    }

    override fun onStoppedTyping() {
      omegle.getExecutor().submit {
        listeners.forEach { it.onStoppedTyping(chat) }
      }
    }
  }
}
