rootProject.name = "playerexpansion-parent"

include("playerexpansion-paper")
include("playerexpansion-velocity")

project(":playerexpansion-velocity").projectDir = file("velocity")
project(":playerexpansion-paper").projectDir = file("paper")
