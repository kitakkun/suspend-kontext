rootProject.name = "suspend-kontext"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("gradle-conventions-settings")
    includeBuild("gradle-conventions")
}

plugins {
    id("settings-conventions")
}

include(":core")
include(":compiler:common")
include(":compiler:cli")
include(":compiler:k2")
include(":compiler:backend")
include(":compiler-test")
include(":gradle-plugin")

