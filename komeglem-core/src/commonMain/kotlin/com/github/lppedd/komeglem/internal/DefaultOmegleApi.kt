package com.github.lppedd.komeglem.internal

import com.github.lppedd.komeglem.*
import com.github.lppedd.komeglem.OmegleEvent.*
import com.github.lppedd.komeglem.ext.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*

/**
 * @author Edoardo Luppi
 */
internal class DefaultOmegleApi(
  private var chatServer: String,
  private val antiNudeServer: String,
) : OmegleApi {
  internal companion object Companion {
    // 2-9 and A-Z, but not I and O
    private const val CHARS_RANDOM_ID = "ABCDEFGHJKLMNPQRSTUVWXTZ23456789"

    private val httpClient = HttpClient {
      install(HttpTimeout) {
        requestTimeoutMillis = 15000 /* 15 seconds */
      }

      install(ContentNegotiation) {
        json()
      }
    }

    suspend fun status(): OmegleStatus {
      val httpResponse = httpClient.post("https://omegle.com/status")
      checkResponse(httpResponse, null /* Don't check the body */)
      return parseStatusInfo(httpResponse.body<JsonObject>())
    }

    private fun parseStatusInfo(json: JsonObject): OmegleStatus =
      OmegleStatus(
        count = json.getInt("count"),
        forceUnmonitored = json.getBooleanOrElse("force_unmon", false),
        antiNudeServers = json.getStringList("antinudeservers"),
        spyQueueTime = json.getDouble("spyQueueTime"),
        spyeeQueueTime = json.getDouble("spyeeQueueTime"),
        rtmfp = json.getString("rtmfp"),
        antiNudePercent = json.getDouble("antinudepercent"),
        timestamp = json.getDouble("timestamp"),
        servers = json.getStringList("servers"),
      )

    private suspend fun checkResponse(httpResponse: HttpResponse, expectedBody: String?) {
      val status = httpResponse.status.value

      if (status !in 200..299) {
        val url = httpResponse.request.url
        val body = httpResponse.body<String>()
        val normalizedBody = body.ifBlank { "(empty body)" }
        throw OmegleApiException("Error calling $url - status: $status, body: $normalizedBody")
      }

      if (expectedBody != null) {
        val body = httpResponse.body<String>()

        if (expectedBody != body) {
          val url = httpResponse.request.url
          val normalizedBody = body.ifBlank { "(empty body)" }
          throw OmegleApiException("Error calling $url - status: $status, body: $normalizedBody")
        }
      }
    }
  }

  override suspend fun status(randomId: String): OmegleStatus {
    val parameters = parametersOf("randid", randomId)
    val httpResponse = httpClient.submitForm("$chatServer/status", parameters)
    checkResponse(httpResponse, null /* Don't check the body */)
    return parseStatusInfo(httpResponse.body<JsonObject>())
  }

  override suspend fun connect(options: OmegleConnectOptions): OmegleConnection {
    val url = "$chatServer/start"
    val randomId = getRandomId()
    val httpResponse = httpClient.post(url) {
      parameter("caps", "recaptcha2,t3")
      parameter("firstevents", 1)
      parameter("spid", "")
      parameter("randid", randomId)
      parameter("lang", options.language)
      parameter("cc", getAntiNudeRandomId())

      if (options.unmonitored) {
        parameter("group", "unmon")
      }

      if (options.topics.isNotEmpty()) {
        val topicsValue = options.topics.joinToString(
          separator = ",",
          prefix = "[",
          postfix = "]",
          transform = { "\"$it\"" },
        )

        parameter("topics", topicsValue)
      }
    }

    val status = httpResponse.status.value
    val body = httpResponse.body<JsonObject>()

    if (status !in 200..299 || body.isEmpty()) {
      throw OmegleApiException("Error calling $url - status: $status")
    }

    val clientId = body.getString("clientID")
    val eventsJson = body["events"]?.jsonArray
    val events = if (eventsJson != null) {
      parseEvents(eventsJson)
    } else {
      emptyList()
    }

    return OmegleConnection(randomId, clientId, events)
  }

  override suspend fun events(clientId: String): List<OmegleEvent> {
    try {
      val url = "$chatServer/events"
      val parameters = parametersOf("id", clientId)
      val httpResponse = httpClient.submitForm(url, parameters) {
        timeout {
          requestTimeoutMillis = 60000 /* 1 minute */
        }
      }

      checkResponse(httpResponse, null /* Don't check the body */)
      return parseEvents(httpResponse.body<JsonArray>())
    } catch (ignored: HttpRequestTimeoutException) {
      // The server blocks the connection until an event happens.
      // If we are here it means no events happened during the
      // 1-minute timeout, but it doesn't mean the chat is dead,
      // so we just swallow the exception and return an empty list
      return emptyList()
    }
  }

  override suspend fun startTyping(clientId: String) {
    val parameters = parametersOf("id", clientId)
    val httpResponse = httpClient.submitForm("$chatServer/typing", parameters)
    checkResponse(httpResponse, "win")
  }

  override suspend fun stopTyping(clientId: String) {
    val parameters = parametersOf("id", clientId)
    val httpResponse = httpClient.submitForm("$chatServer/typing", parameters)
    checkResponse(httpResponse, "win")
  }

  override suspend fun sendMessage(clientId: String, message: String) {
    val parameters = Parameters.build {
      append("id", clientId)
      append("msg", message)
    }

    val httpResponse = httpClient.submitForm("$chatServer/send", parameters)
    checkResponse(httpResponse, "win")
  }

  override suspend fun stopLookingForCommonLikes(clientId: String) {
    val parameters = parametersOf("id", clientId)
    val httpResponse = httpClient.submitForm("$chatServer/stoplookingforcommonlikes", parameters)
    checkResponse(httpResponse, "win")
  }

  override suspend fun disconnect(clientId: String) {
    val parameters = parametersOf("id", clientId)
    val httpResponse = httpClient.submitForm("$chatServer/disconnect", parameters)
    checkResponse(httpResponse, "win")
  }

  private fun parseEvents(eventsJson: JsonArray): List<OmegleEvent> =
    // JsonArray is a List<JsonElement>
    eventsJson.asSequence()
      .filterIsInstance<JsonArray>()
      .map(::parseEvent)
      .toList()

  private fun parseEvent(event: JsonArray): OmegleEvent =
    when (val type = event[0].str) {
      "waiting"              -> Waiting
      "identDigests"         -> IdentDigests(parseIdentDigests(event[1]))
      "connected"            -> Connected
      "serverMessage"        -> ServerMessage(event[1].str)
      "typing"               -> StrangerStartedTyping
      "stoppedTyping"        -> StrangerStoppedTyping
      "gotMessage"           -> StrangerMessage(event[1].str)
      "commonLikes"          -> CommonLikes(parseCommonLikes(event[1].jsonArray))
      "strangerDisconnected" -> Disconnected
      "recaptchaRequired"    -> ReCaptchaRequired(event[1].str)
      "recaptchaRejected"    -> ReCaptchaRejected(event[1].str)
      "statusInfo"           -> StatusInfo(parseStatusInfo(event[1].jsonObject))
      "error"                -> Error(event[1].str)
      else                   -> throw UnsupportedOperationException("Unknown event type: $type")
    }

  private fun parseIdentDigests(value: JsonElement): List<String> =
    value.str.split(",")

  private fun parseCommonLikes(value: JsonArray): List<String> =
    value.getStringList()

  // An 8 chars string
  private fun getRandomId(): String =
    buildString {
      for (i in 0..<8) {
        append(CHARS_RANDOM_ID.random())
      }
    }

  private suspend fun getAntiNudeRandomId(): String {
    val httpResponse = httpClient.post("$antiNudeServer/check")
    checkResponse(httpResponse, null /* Don't check the body */)
    return httpResponse.body<String>()
  }
}
