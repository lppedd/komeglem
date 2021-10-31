package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
data class OmegleConnection(val clientId: String, val events: List<OmegleEvent>)
