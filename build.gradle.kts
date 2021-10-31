plugins {
  kotlin("multiplatform") version "1.6.0-RC"
}

group = "com.github.lppedd"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "1.8"
    }

    withJava()
    testRuns["test"].executionTask.configure {
      useJUnitPlatform()
    }
  }

  js(BOTH) {
    browser {
      commonWebpackConfig {
        cssSupport.enabled = true
      }
    }
  }

  @Suppress("unused_variable")
  sourceSets {
    all {
      languageSettings.apply {
        optIn("kotlin.time.ExperimentalTime")
      }
    }

    val commonMain by getting
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation("com.konghq:unirest-java:3.13.0")
      }
    }

    val jvmTest by getting
    val jsMain by getting
    val jsTest by getting
  }
}
