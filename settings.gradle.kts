enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "MiniPlaceholders-Player-Expansion"

arrayOf("paper", "velocity", "common").forEach {
    include("player-expansion-$it")

    project(":player-expansion-$it").projectDir = file(it)
}
