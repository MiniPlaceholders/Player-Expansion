import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

allprojects {
    apply(plugin = "java")
    group = "me.dreamerzero.playerexpansion"
    version = "1.0.0"
    description = "Player-Expansion"
}

dependencies {
    shadow(project(":playerexpansion-velocity"))
    shadow(project(":playerexpansion-paper"))
}

subprojects {
    repositories {
	mavenCentral()
        maven("https://jitpack.io")
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        compileOnly("com.github.4drian3d:MiniPlaceholders:1.1.1")
    }
}

tasks {
    shadowJar {
        archiveFileName.set("Player-Expansion.jar")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        configurations = listOf(project.configurations.shadow.get())
    }
    build {
        dependsOn(shadowJar)
    }
}
