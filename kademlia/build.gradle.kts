plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "at.crowdware"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.microutils:kotlin-logging:1.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("com.google.code.gson:gson:2.8.9")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

tasks.jar {
    archiveFileName.set("kademlia.jar")
    destinationDirectory.set(file("libs"))
}

tasks.build {
    // Dependency between the build task and the jar task
    dependsOn(tasks.jar)
}