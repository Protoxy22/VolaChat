import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.jvm.tasks.Jar
import java.io.*
import java.util.spi.*

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
    implementation(kotlin("stdlib"))

    //Compose
    implementation(compose.desktop.currentOs)

    //Ktor Network, for TCP networking server/client
    implementation("io.ktor:ktor-network:2.0.0")
    implementation("io.ktor:ktor-network-tls:2.0.0")

    //Ktor Client
    implementation("io.ktor:ktor-client-core:2.0.0")
    implementation("io.ktor:ktor-client-cio:2.0.0")
    implementation("io.ktor:ktor-client-auth:2.0.0")

    implementation("io.ktor:ktor-serialization-gson:2.0.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.0.0")

    // Module dependencies
    // Dagger : A fast dependency injector for Android and Java.
    api("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    kaptTest("com.google.dagger:dagger-compiler:$daggerVersion")

    // Cyclone : https://github.com/theapache64/cyclone
    implementation("com.theapache64:cyclone:1.0.0-alpha01")

    // Decompose : Decompose
    val decomposeVersion = "0.2.5"
    implementation("com.arkivanov.decompose:decompose-jvm:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains-jvm:$decomposeVersion")
    // Arbor : Like Timber, just different.
    api("com.ToxicBakery.logging:arbor-jvm:1.34.109")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "net.volachat.AppKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

compose.desktop {
    application {
        mainClass = "net.volachat.AppKt"
        nativeDistributions {
            modules("jdk.crypto.ec")

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

val printModuleDeps by tasks.creating {
    doLast {
        val uberJar = tasks.named("packageUberJarForCurrentOS", Jar::class)
        val jarFile = uberJar.get().archiveFile.get().asFile

        val jdeps = ToolProvider.findFirst("jdeps").orElseGet { error("Can't find jdeps tool in JDK") }
        val out = StringWriter()
        val pw = PrintWriter(out)
        jdeps.run(pw, pw, "--print-module-deps", "--ignore-missing-deps", jarFile.absolutePath)

        val modules = out.toString()
        println(modules)
        // compose.desktop.application.nativeDistributions.modules.addAll(modules.split(","))
    }
    dependsOn("packageUberJarForCurrentOS")
}