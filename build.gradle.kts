import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.5.31"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    id("org.jetbrains.compose") version "1.0.0-beta1"
}

group = "net.volachat"
version = "1.0.0"

repositories {
    jcenter()
    mavenCentral()
    google()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

val daggerVersion by extra("2.39.1")

dependencies {
    implementation(compose.desktop.currentOs)

    // Module dependencies
    implementation(project(":data"))

    // Dagger : A fast dependency injector for Android and Java.
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    kaptTest("com.google.dagger:dagger-compiler:$daggerVersion")

    // Cyclone : https://github.com/theapache64/cyclone
    implementation("com.theapache64:cyclone:1.0.0-alpha01")

    // Decompose : Decompose
    val decomposeVersion = "0.2.5"
    implementation("com.arkivanov.decompose:decompose-jvm:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains-jvm:$decomposeVersion")

    implementation ("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.2")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "net.volachat.AppKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "volachat"
            packageVersion = "1.0.0"

            val iconsRoot = project.file("src/main/resources/drawables")

            linux {
                iconFile.set(iconsRoot.resolve("launcher_icons/linux.png"))
            }

            windows {
                iconFile.set(iconsRoot.resolve("launcher_icons/windows.ico"))
            }

            macOS {
                iconFile.set(iconsRoot.resolve("launcher_icons/macos.icns"))
            }

        }
    }
}