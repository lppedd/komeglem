# komeglem

**komeglem** is an attempt to a multiplatform client for the popular Omegle chatroom.  
The project is developed just for fun, and to try out Kotlin Multiplatform capabilities.

-----

An example of how to start chatting with a random stranger:

```kotlin
val listener = object : OmegleRandomChatListener {
  override suspend fun onWaiting(chat: OmegleRandomChat) {
    println("Waiting")
  }

  override suspend fun onConnected(chat: OmegleRandomChat) {
    println("Connected: ${chat.getClientId()}")
  }

  override suspend fun onDisconnected(chat: OmegleRandomChat) {
    println("Disconnected")
  }

  override suspend fun onMessage(chat: OmegleRandomChat, message: String) {
    println("Message: $message")
  }
}

val omegle = omegle(/* Optional custom OmegleApiFactory */)
val session = omegle.newRandomChat(listener, language = "en")

// At some point you may want to forcefully disconnect the chat session
session.disconnect()

// Remember to dispose the Omegle instance when not needed anymore
omegle.dispose()
```
