package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface Omegle {
  public fun getApiFactory(): OmegleApiFactory
  public fun newRandomChat(listener: OmegleRandomChatListener): OmegleRandomChatSession
  public fun newTopicsChat(listener: OmegleTopicsChatListener): OmegleTopicsChatSession
  public fun dispose()
}
