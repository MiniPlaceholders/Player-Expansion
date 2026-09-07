plugins {
    id("net.fabricmc.fabric-loom")
}

dependencies {
    compileOnly(libs.miniplaceholders)
    compileOnly(projects.playerExpansionCommon)
    minecraft(libs.minecraft)
    compileOnly(libs.fabric.loader)
    compileOnly(libs.fabric.api)
    compileOnly(libs.adventure.platform.fabric)
}