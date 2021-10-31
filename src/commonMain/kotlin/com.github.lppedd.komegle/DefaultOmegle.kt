package com.github.lppedd.komegle

import com.github.lppedd.komegle.session.DefaultOmegleRandomChatSession
import com.github.lppedd.komegle.session.DefaultOmegleTopicsChatSession

/**
 * @author Edoardo Luppi
 */
internal class DefaultOmegle(
  private val apiProvider: OmegleApiProvider,
  private val errorHandler: OmegleErrorHandler,
  private val executor: OmegleExecutor,
) : Omegle {
  override fun newRandomChat(): OmegleRandomChatSession =
    DefaultOmegleRandomChatSession(this)

  override fun newTopicsChat(vararg topics: String): OmegleTopicsChatSession =
    DefaultOmegleTopicsChatSession(this, topics)

  override fun getApiProvider(): OmegleApiProvider =
    apiProvider

  override fun getErrorHandler(): OmegleErrorHandler =
    errorHandler

  override fun getExecutor(): OmegleExecutor =
    executor

  override fun dispose() {
    executor.dispose()
  }
}
