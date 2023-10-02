package com.github.lppedd.komeglem.internal

import com.github.lppedd.komeglem.*

/**
 * @author Edoardo Luppi
 */
internal class DefaultOmegle(override val apiFactory: OmegleApiFactory) : Omegle {
  override suspend fun newRandomChat(
    listener: OmegleRandomChatListener,
    language: String,
  ): OmegleRandomChatSession {
    val session = DefaultOmegleRandomChatSession(this, listener)
    session.connect(language)
    return session
  }

  override suspend fun newTopicsChat(
    listener: OmegleTopicsChatListener,
    language: String,
    vararg topics: String,
  ): OmegleTopicsChatSession {
    if (topics.isEmpty()) {
      throw OmegleException("The topic list cannot be empty")
    }

    val session = DefaultOmegleTopicsChatSession(this, listener)
    session.connect(language, topics.toList())
    return session
  }

  override fun dispose() {
    // Noop
  }
}
