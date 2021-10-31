package com.github.lppedd.komegle

import com.github.lppedd.komegle.OmegleEvent.*
import kong.unirest.Unirest
import kong.unirest.json.JSONArray
import kong.unirest.json.JSONObject

/**
 * @author Edoardo Luppi
 */
actual class DefaultOmegleApi actual constructor() : OmegleApi {
  private val server = getRandomServer()

  actual override fun status(): OmegleStatus {
    val response = Unirest.post("https://omegle.com/status").asJson()
    return parseStatusInfo(response.body.`object`)
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
    val eventsJson = json.optJSONArray("events")
    val events = if (eventsJson != null) {
      parseEvents(eventsJson)
    } else {
      emptyList()
    }

    return OmegleConnection(clientId, events)
  }

  actual override fun events(clientId: String): List<OmegleEvent> {
    val response = Unirest.post("$server/events")
      .field("id", clientId)
      .asJson()

    val eventsJson = response.body.array
    return parseEvents(eventsJson)
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

  private fun parseEvents(eventsJson: JSONArray): List<OmegleEvent> =
    eventsJson.asSequence()
      .filterIsInstance<JSONArray>()
      .map(::parseEvent)
      .toList()

  private fun parseEvent(event: JSONArray): OmegleEvent {
    val type = event.getString(0)
    val value = event.opt(1)
    return when (type) {
      "waiting" -> Waiting
      "identDigests" -> IdentDigests(parseIdentDigests(value))
      "connected" -> Connected
      "serverMessage" -> ServerMessage(value as String)
      "typing" -> StrangerStartedTyping
      "stoppedTyping" -> StrangerStoppedTyping
      "gotMessage" -> StrangerMessage(value as String)
      "commonLikes" -> CommonLikes(parseCommonLikes(value))
      "strangerDisconnected" -> Disconnected
      "recaptchaRequired" -> ReCaptchaRequired(value as String)
      "recaptchaRejected" -> ReCaptchaRejected
      "statusInfo" -> StatusInfo(parseStatusInfo(value))
      else -> throw UnsupportedOperationException("Unknown event type: $type")
    }
  }

  @Suppress("unchecked_cast")
  private fun parseStatusInfo(value: Any): OmegleStatus {
    val json = value as JSONObject
    return OmegleStatus(
      json.getInt("count"),
      json.getJSONArray("antinudeservers").toList() as List<String>,
      json.getDouble("spyQueueTime"),
      json.getDouble("spyeeQueueTime"),
      json.getString("rtmfp"),
      json.getDouble("antinudepercent"),
      json.getDouble("timestamp"),
      json.getJSONArray("servers").toList() as List<String>
    )
  }

  private fun parseIdentDigests(value: Any): List<String> {
    val ids = value as String
    return ids.split(",")
  }

  @Suppress("unchecked_cast")
  private fun parseCommonLikes(value: Any): List<String> {
    val array = value as JSONArray
    return array.toList() as List<String>
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
