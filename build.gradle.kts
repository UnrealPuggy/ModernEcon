plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "3.0.2"

}

group = "io.github.unrealpuggy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
        maven {
            name = "papermc"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }

}

tasks {
    runServer {
        minecraftVersion("26.2")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")

}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}