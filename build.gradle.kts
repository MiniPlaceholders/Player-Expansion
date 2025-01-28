plugins {
    java
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(projects.playerExpansionPaper)
    implementation(projects.playerExpansionVelocity)
}

subprojects {
    apply<JavaPlugin>()
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")
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
