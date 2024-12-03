package com.kitakkun.suspendkontext.gradle

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

/**
 * Note: recommend to run `./gradlew publishToMavenLocal` before running this test.
 * Otherwise, artifacts which are published to the MavenCentral will be preferentially used.
 * I wanted to publish it automatically before test runs, but couldn't achieve easily.
 */
class SuspendKontextGradlePluginTest {
    @TempDir
    private lateinit var testProjectDir: File
    private lateinit var settingsFile: File
    private lateinit var buildFile: File
    private lateinit var sourceDir: File
    private lateinit var sourceFile: File

    @BeforeEach
    fun setup() {
        settingsFile = File(testProjectDir, "settings.gradle.kts")
        buildFile = File(testProjectDir, "build.gradle.kts")
        sourceDir = File(testProjectDir, "src/main/kotlin")
        sourceDir.mkdirs()
        sourceFile = File(sourceDir, "Main.kt")
    }

    @Test
    fun test() {
        settingsFile.writeText(
            """
            pluginManagement {
                repositories {
                    mavenCentral()
                }
            }

            dependencyResolutionManagement {
                repositories {
                    mavenLocal() // should be prioritized to use local version.
                    mavenCentral()
                }
            }

            rootProject.name = "sandbox"
            """.trimIndent(),
        )
        buildFile.writeText(
            """
            plugins {
                id("org.jetbrains.kotlin.jvm") version "2.0.0"
                id("com.kitakkun.suspend-kontext")
                application
            }

            suspendKontext {
                enabled = true
            }
            
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
            }
            
            application {
                mainClass = "MainKt"
            }
            """.trimIndent(),
        )
        sourceFile.writeText(
            """
            import com.kitakkun.suspendkontext.core.annotation.IOContext
            import kotlinx.coroutines.runBlocking
            import kotlin.coroutines.coroutineContext
            
            fun main() {
                runBlocking {
                    ioProcess()
                }
            }
            
            @IOContext
            suspend fun ioProcess() {
                println("Hello, IO!")
                println(coroutineContext)
            }
            """.trimIndent(),
        )

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments("run")
            .withPluginClasspath()
            .run()

        println(result.output)
        Assertions.assertEquals(TaskOutcome.SUCCESS, result.task(":run")?.outcome)
    }
}
