@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.backend.koin

import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.openapitools.generator.gradle.plugin.OpenApiGeneratorPlugin
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

class BackendKoinLibraryOpenApiPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendKoinLibraryPlugin>()
		apply<OpenApiGeneratorPlugin>()

		val openApiSpecDir = projectDir.resolve("openapi")

		configure<SourceSetContainer> {
			named("main") {
				java.srcDir("$buildDir/generated-sources/kotlin")
				resources.srcDir(openApiSpecDir)
			}
		}

		tasks {
			val generateOpenApiKotlin by creating(GenerateTask::class) {
				group = "openapi"

				generatorName.set("kotlin")

				validateSpec.set(true)

				inputSpec.set("$openApiSpecDir/root.json")
				outputDir.set("$buildDir/generated-sources/kotlin")

				modelNameSuffix.set("Model")

				configOptions.set(
					mapOf(
						"enumPropertyNaming" to "UPPERCASE",
						"dateLibrary" to "java8-localdatetime",
						"collectionType" to "list",
						"serializationLibrary" to "jackson",
						"library" to "multiplatform",
						"sourceFolder" to ""
					)
				)
				typeMappings.set(mapOf("double" to "java.math.BigDecimal"))
				systemProperties.set(mapOf("modelDocs" to "false", "apis" to "false", "models" to ""))
			}

			named("compileKotlin") { dependsOn(generateOpenApiKotlin) }

			withType<Detekt> { enabled = false }

			named("processResources") { dependsOn(generateOpenApiKotlin) }
		}
	}
}
