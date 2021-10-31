# komeglem

`komeglem` is an attempt to a multiplatform client for the popular Omegle chatroom.  
The project itself was born just for fun, and to try out the Kotlin's multiplatform capabilities.  
My priorities now are:
- to design an API that is easy to use, that avoids logical errors, and is extendable
- to offer the same API for all supported platforms

-----

An example of how to start chatting:

```kotlin
val listener = object : OmegleChatListener {
  override fun onWaiting(chat: OmegleChat) {
    println("Waiting")
  }

  override fun onConnected(chat: OmegleChat) {
    println("Connected: ${chat.getClientId()}")
  }

  override fun onDisconnected(chat: OmegleChat) {
    println("Disconnected")
  }

  override fun onMessage(chat: OmegleChat, message: String) {
    println("Message: $message")
  }
}

val omegle = OmegleBuilder().build()
val chatSession = omegle.newRandomChat()
chatSession.addListener(listener)
chatSession.connect()

// Remember to always dispose the Omegle instance
omegle.dispose()
```
