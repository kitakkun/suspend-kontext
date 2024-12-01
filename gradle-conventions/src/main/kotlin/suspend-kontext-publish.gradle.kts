import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost

open class SuspendKontextPublicationExtension {
    var artifactId: String = ""
}

extensions.create<SuspendKontextPublicationExtension>("suspendKontextPublication")

plugins {
    id("com.vanniktech.maven.publish")
}

afterEvaluate {
    val extension = extensions.getByType(SuspendKontextPublicationExtension::class.java)
    val artifactId = extension.artifactId
    if (artifactId.isBlank()) error("Artifact ID must be specified.")

    configure<MavenPublishBaseExtension> {
        coordinates(artifactId = artifactId)

        pom {
            name.set("suspend-kontext")
            description.set("suspend-kontext is a Kotlin Compiler Plugin that allows you to specify the default coroutine context for suspend functions using annotations.")
            inceptionYear.set("2024")
            url.set("https://github.com/kitakkun/suspend-kontext")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("https://github.com/kitakkun/suspend-kontext/blob/master/LICENSE")
                    distribution.set("repo")
                }
                developers {
                    developer {
                        id.set("kitakkun")
                        name.set("kitakkun")
                        url.set("https://github.com/kitakkun")
                    }
                }
                scm {
                    url.set("https://github.com/kitakkun/suspend-kontext")
                    connection.set("scm:git:git://github.com/kitakkun/suspend-kontext.git")
                    developerConnection.set("scm:git:ssh://git@github.com/kitakkun/suspend-kontext.git")
                }
            }
        }

        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
        signAllPublications()

        // avoid failure when executing publishToMavenLocal
        tasks.withType(org.gradle.plugins.signing.Sign::class).configureEach {
            onlyIf {
                !gradle.startParameter.taskNames.toString().contains("publishToMavenLocal")
            }
        }
    }
}
