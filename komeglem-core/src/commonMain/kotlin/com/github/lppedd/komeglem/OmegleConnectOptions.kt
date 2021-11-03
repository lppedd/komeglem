package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public data class OmegleConnectOptions(
  /**
   * The two-chars language code representing the preferred chat language.
   *
   * Examples: `en`, `it`
   */
  val language: String = "en",

  /**
   * An *optionally* non-empty list of topics that must be shared with the stranger.
   */
  val topics: List<String> = emptyList(),

  /**
   * Whether to join the unmonitored section of Omegle.
   */
  val unmonitored: Boolean = false,
)
