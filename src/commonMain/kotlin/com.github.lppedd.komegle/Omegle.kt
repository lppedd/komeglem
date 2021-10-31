package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
interface Omegle {
  fun getApiProvider(): OmegleApiProvider
  fun getErrorHandler(): OmegleErrorHandler
  fun getExecutor(): OmegleExecutor
  fun newRandomChat(): OmegleRandomChatSession
  fun newTopicsChat(vararg topics: String): OmegleTopicsChatSession
  fun dispose()
}
