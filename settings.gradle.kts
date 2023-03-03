rootProject.name = "Player-Expansion"

arrayOf("paper", "velocity").forEach {
    include("player-expansion-$it")

    project(":player-expansion-$it").projectDir = file(it)
}
