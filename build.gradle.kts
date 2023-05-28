plugins {
    java
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":player-expansion-velocity"))
    implementation(project(":player-expansion-paper"))
}

subprojects {
    apply<JavaPlugin>()
    repositories {
        maven("https://papermc.io/repo/repository/maven-public/")
    }
    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(17)
        }
    }
}

tasks {
    shadowJar {
        archiveFileName.set("MiniPlaceholders-Player-Expansion-${project.version}.jar")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    build {
        dependsOn(shadowJar)
    }
}
