package com.github.lppedd.komeglem.internal

import com.github.lppedd.komeglem.*

/**
 * @author Edoardo Luppi
 */
internal class DefaultOmegle(private val omegleApiFactory: OmegleApiFactory) : Omegle {
  override fun getApiFactory(): OmegleApiFactory =
    omegleApiFactory

  override fun newRandomChat(listener: OmegleRandomChatListener): OmegleRandomChatSession =
    DefaultOmegleRandomChatSession(this, listener)

  override fun newTopicsChat(listener: OmegleTopicsChatListener): OmegleTopicsChatSession =
    DefaultOmegleTopicsChatSession(this, listener)

  override fun dispose() {
    // Noop
  }
}
