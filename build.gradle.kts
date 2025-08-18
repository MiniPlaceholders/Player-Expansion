plugins {
    java
    alias(libs.plugins.idea.ext)
    alias(libs.plugins.blossom)
    alias(libs.plugins.shadow)
}

dependencies {
    compileOnly(libs.miniplaceholders)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimessage)
    implementation(projects.playerExpansionPaper)
    implementation(projects.playerExpansionVelocity)
    implementation(projects.playerExpansionFabric)
    implementation(projects.playerExpansionSponge)
    implementation(projects.playerExpansionCommon)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }
}

subprojects {
    apply<JavaPlugin>()
    java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(21)
        }
    }
}

tasks {
    shadowJar {
        archiveFileName.set("Player-Expansion-${project.version}.jar")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    build {
        dependsOn(shadowJar)
    }
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", project.version.toString())
            }
        }
    }
}