plugins {
    alias(libs.plugins.blossom)
    alias(libs.plugins.runvelocity)
}

dependencies {
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
    compileOnly(libs.miniplaceholders)
}

blossom {
    replaceTokenIn("src/main/java/io/github/miniplaceholders/expansion/player/velocity/Constants.java")
    replaceToken("{version}", project.version)
}
