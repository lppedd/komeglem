package com.github.lppedd.komegle.event

/**
 * A listener for all the events sent by Omegle.
 *
 * @author Edoardo Luppi
 */
internal interface OmegleEventListener {
  fun onWaiting()
  fun onIdentDigests(digests: List<String>)
  fun onCommonLikes(topics: List<String>)
  fun onConnected()
  fun onDisconnected()
  fun onServerMessage(message: String)
  fun onMessage(message: String)
  fun onStartedTyping()
  fun onStoppedTyping()
}
