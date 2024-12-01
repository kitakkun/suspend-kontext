rootProject.name = "suspend-kontext"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("$rootDir/versions-root/libs.versions.toml"))
        }
    }
}

include(":core")
include(":compiler:common")
include(":compiler:cli")
include(":compiler:k2")
include(":compiler:backend")
include(":compiler-test")
