package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
actual class DefaultOmegleApi : OmegleApi {
  actual override fun status(): OmegleStatus {
    TODO("Not yet implemented")
  }

  actual override fun connect(vararg topics: String): OmegleConnection {
    TODO("Not yet implemented")
  }

  actual override fun events(clientId: String): Any {
    TODO("Not yet implemented")
  }

  actual override fun startTyping(clientId: String) {
    TODO("Not yet implemented")
  }

  actual override fun stopTyping(clientId: String) {
    TODO("Not yet implemented")
  }

  actual override fun sendMessage(clientId: String, message: String) {
    TODO("Not yet implemented")
  }

  actual override fun stopLookingForCommonLikes(clientId: String) {
    TODO("Not yet implemented")
  }

  actual override fun disconnect(clientId: String) {
    TODO("Not yet implemented")
  }
}
