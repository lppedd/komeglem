package com.github.lppedd.komeglem

/**
 * @author Edoardo Luppi
 */
public interface OmegleTopicsChat : OmegleRandomChat {
  public suspend fun stopLookingForCommonTopics()
}
