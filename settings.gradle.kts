rootProject.name = "komeglem"

pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

include(":komeglem-core")
include(":komeglem-desktop")
