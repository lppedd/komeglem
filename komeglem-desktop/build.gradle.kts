import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("jvm")
  alias(libs.plugins.compose.multiplatform)
}

repositories {
  mavenCentral()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  google()
}

dependencies {
  implementation(compose.desktop.currentOs)
  implementation(project(":komeglem-core"))
}

compose.desktop {
  application {
    mainClass = "com.github.lppedd.komeglem.desktop.MainKt"

    nativeDistributions {
      targetFormats(
        TargetFormat.Exe,
        TargetFormat.Deb,
        TargetFormat.Dmg,
      )

      packageName = "komeglem-desktop"
      packageVersion = project.version.toString().replace("-SNAPSHOT", "")
    }
  }
}
