plugins {
    alias(libs.plugins.pluginyml)
    alias(libs.plugins.runpaper)
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.miniplaceholders)
}

bukkit {
    main = "io.github.miniplaceholders.expansion.player.paper.PaperPlugin"
    apiVersion = "1.19"
    authors = listOf("4drian3d")
    depend = listOf("MiniPlaceholders")
    version = project.version as String
}
