package com.github.lppedd.komegle

import kong.unirest.Unirest

/**
 * @author Edoardo Luppi
 */
actual class DefaultOmegleApi actual constructor() : OmegleApi {
  private val server = getRandomServer()

  actual override fun status(): OmegleStatus {
    val response = Unirest
      .post("https://omegle.com/status")
      .asObject(OmegleStatus::class.java)
    return response.body
  }

  actual override fun connect(vararg topics: String): OmegleConnection {
    val builder = Unirest.post("$server/start")
      .queryString("caps", "recaptcha2,t")
      .queryString("firstevents", 1)
      .queryString("spid", "")
      .queryString("randid", getRandomId())
      .queryString("lang", "it")

    if (topics.isNotEmpty()) {
      builder.queryString("topics", topics.joinToString(
        separator = ",",
        prefix = "[",
        postfix = "]",
        transform = { "\"$it\"" }
      ))
    }

    val response = builder.asJson()
    val json = response.body.`object`

    if (json.isEmpty) {
      throw OmegleApiException("Error connecting to the server $server")
    }

    val clientId = json.getString("clientID")
    val events = json.optJSONArray("events")
    return OmegleConnection(clientId, events)
  }

  actual override fun events(clientId: String): Any {
    val response = Unirest.post("$server/events")
      .field("id", clientId)
      .asJson()
    return response.body.array
  }

  actual override fun startTyping(clientId: String) {
    Unirest.post("$server/typing")
      .field("id", clientId)
      .asEmpty()
  }

  actual override fun stopTyping(clientId: String) {
    Unirest.post("$server/typing")
      .field("id", clientId)
      .asEmpty()
  }

  actual override fun sendMessage(clientId: String, message: String) {
    Unirest.post("$server/send")
      .field("id", clientId)
      .field("msg", message)
      .asEmpty()
  }

  actual override fun stopLookingForCommonLikes(clientId: String) {
    Unirest.post("$server/stoplookingforcommonlikes")
      .field("id", clientId)
      .asEmpty()
  }

  actual override fun disconnect(clientId: String) {
    val response = Unirest.post("$server/disconnect")
      .field("id", clientId)
      .asString()

    if (response.body != "win") {
      throw OmegleApiException("Error during disconnection: ${response.body}")
    }
  }

  private fun getRandomServer() =
    "https://${status().servers.random()}.omegle.com"

  @Suppress("SpellCheckingInspection")
  private fun getRandomId(): String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
    return (1..8)
      .map { charset.random() }
      .joinToString("")
  }
}
