package com.github.lppedd.komegle.session

import com.github.lppedd.komegle.*
import com.github.lppedd.komegle.chat.OmegleTopicsRandomChat
import com.github.lppedd.komegle.event.OmegleEventDispatcher
import com.github.lppedd.komegle.event.OmegleEventListener

/**
 * @author Edoardo Luppi
 */
internal class DefaultOmegleTopicsChatSession(
  private val omegle: Omegle,
  private val topics: Array<out String>,
) : OmegleTopicsChatSession {
  private val listeners = LinkedHashSet<OmegleTopicsChatListener>(4, 1F)

  private var isConnected = false
  private lateinit var dispatcher: OmegleEventDispatcher
  private lateinit var chat: OmegleTopicsChat

  override fun addListener(listener: OmegleTopicsChatListener) {
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
      dispatcher.stop()
    }
  }

  override fun isConnected(): Boolean =
    isConnected

  private fun connectInternal() {
    val api = omegle.getApiProvider().getApi()
    val (clientId, events) = api.connect(*topics)
    val dispatcher = OmegleEventDispatcher(omegle.getExecutor(), api, clientId)
    dispatcher.addListener(TopicsOmegleEventListener())
    chat = OmegleTopicsRandomChat(api, clientId, dispatcher)
    dispatcher.dispatchEvents(events)
    dispatcher.start()
  }

  private inner class TopicsOmegleEventListener : OmegleEventListener {
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
      omegle.getExecutor().submit {
        listeners.forEach { it.onCommonLikes(chat, topics) }
      }
    }

    override fun onConnected() {
      omegle.getExecutor().submit {
        listeners.forEach { it.onConnected(chat) }
        dispatcher.stop()
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
