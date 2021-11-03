package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface OmegleApiFactory {
  /**
   * Returns an instance of [OmegleApi] ready to be used.
   *
   * The factory must properly initialize the API instance
   * before returning it to the caller.
   */
  public suspend fun create(): OmegleApi
}
