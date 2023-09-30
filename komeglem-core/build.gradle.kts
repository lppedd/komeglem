import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

plugins {
  kotlin("multiplatform")
}

kotlin {
  explicitApi = ExplicitApiMode.Warning

  @Suppress("OPT_IN_USAGE")
  compilerOptions.run {
    languageVersion = KotlinVersion.KOTLIN_1_9
    apiVersion = KotlinVersion.KOTLIN_1_9
  }

  jvmToolchain {
    languageVersion = JavaLanguageVersion.of(17)
    vendor = JvmVendorSpec.AMAZON
  }

  jvm {
    compilations.configureEach {
      compilerOptions.configure {
        jvmTarget = JvmTarget.JVM_1_8
        freeCompilerArgs.add("-Xjvm-default=all")
      }
    }

    testRuns.named("test") {
      executionTask.configure {
        useJUnitPlatform()
      }
    }
  }

  js {
    browser()

    // KT-52578
    yarn.ignoreScripts = false
  }

  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.serialization.kotlinx.json)
      }
    }

    commonTest {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    jvmMain {
      dependencies {
        implementation(libs.ktor.engine.cio)
      }
    }

    jsMain {
      dependencies {
        implementation(libs.ktor.engine.js)
      }
    }
  }
}
