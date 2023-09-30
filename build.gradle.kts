plugins {
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.compose.multiplatform) apply false
}

allprojects {
  group = "com.github.lppedd"
  version = "1.0.0-SNAPSHOT"

  repositories {
    mavenCentral()
  }
}
