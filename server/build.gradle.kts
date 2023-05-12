plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "at.crowdware"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}



kotlin {
    jvmToolchain(17)

    sourceSets {
        val main by getting {
            dependencies {

                implementation(files("../kademlia/libs/kademlia.jar"))
                implementation("com.google.code.gson:gson:2.8.9")

            }
        }
    }
}

application {
    mainClass.set("MainKt")
}