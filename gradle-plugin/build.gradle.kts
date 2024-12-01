plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.buildconfig)
    alias(libs.plugins.suspendKontextPublish)
    `java-gradle-plugin`
    groovy
}


dependencies {
    implementation(libs.kotlin.gradle.plugin)

    testImplementation(gradleTestKit())
    testImplementation("org.spockframework:spock-core:2.2-groovy-3.0") {
        exclude(group = "org.codehaus.groovy")
    }
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

gradlePlugin {
    plugins {
        create("kondition") {
            id = "com.kitakkun.suspend-kontext"
            implementationClass = "com.kitakkun.suspendkontext.gradle.SuspendKontextGradlePlugin"
        }
    }
}

buildConfig {
    buildConfigField("VERSION", libs.versions.suspendKontext.get())
}

suspendKontextPublication {
    artifactId = "gradle-plugin"
}

tasks.test {
    useJUnitPlatform()
}
