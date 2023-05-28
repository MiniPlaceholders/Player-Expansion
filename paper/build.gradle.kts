plugins {
    alias(libs.plugins.runpaper)
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.miniplaceholders)
    implementation(projects.playerExpansionCommon)
}

tasks {
    processResources {
        filesMatching("paper-plugin.yml") {
            expand("version" to project.version)
        }
    }
}
