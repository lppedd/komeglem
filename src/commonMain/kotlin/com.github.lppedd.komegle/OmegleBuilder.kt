package com.github.lppedd.komegle

/**
 * @author Edoardo Luppi
 */
@Suppress("unused")
class OmegleBuilder {
  private var apiProvider: OmegleApiProvider? = null
  private var errorHandler: OmegleErrorHandler? = null
  private var executor: OmegleExecutor? = null

  fun withApiProvider(apiProvider: OmegleApiProvider): OmegleBuilder {
    this.apiProvider = apiProvider
    return this
  }

  fun withErrorHandler(errorHandler: OmegleErrorHandler): OmegleBuilder {
    this.errorHandler = errorHandler
    return this
  }

  fun withExecutor(executor: OmegleExecutor): OmegleBuilder {
    this.executor = executor
    return this
  }

  fun build(): Omegle {
    val apiProvider = apiProvider ?: DefaultOmegleApiProvider()
    val errorHandler = errorHandler ?: ConsoleOmegleErrorHandler()
    val executor = executor ?: DefaultOmegleExecutor()
    return DefaultOmegle(apiProvider, errorHandler, executor)
  }
}
