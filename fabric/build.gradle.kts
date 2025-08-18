plugins {
    id("fabric-loom")
}

dependencies {
    compileOnly(libs.miniplaceholders)
    compileOnly(projects.playerExpansionCommon)
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modCompileOnly(libs.fabric.loader)
    modCompileOnly(libs.fabric.api)
    modCompileOnly(libs.adventure.platform.fabric)
}