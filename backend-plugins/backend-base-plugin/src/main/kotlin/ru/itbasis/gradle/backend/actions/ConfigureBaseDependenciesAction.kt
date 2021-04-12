package ru.itbasis.gradle.backend.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolveDetails
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import ru.itbasis.gradle.backend.utils.getAllPropertiesFromResources
import ru.itbasis.gradle.backend.utils.putExtraKeys

class ConfigureBaseDependenciesAction : Action<Project> {
	override fun execute(target: Project): Unit = target.run {
//		apply<DependencyManagementPlugin>()

		putExtraKeys(this@ConfigureBaseDependenciesAction.getAllPropertiesFromResources(propFileName = "dep-versions.properties"))

//		the<DependencyManagementExtension>().apply {
//			imports {
//				mavenBom(SpringBootPlugin.BOM_COORDINATES)
//			}
//		}

		repositories {
			// Kotlin-Faker
			maven(url = "https://dl.bintray.com/serpro69/maven/")
			maven(url = "https://dl.bintray.com/serpro69/maven-release-candidates/")

			maven(url = "https://kotlin.bintray.com/kotlinx")
		}

		configureResolutionStrategy(target = project)
		configureDependencies(target = project)
	}

	private fun configureResolutionStrategy(target: Project): Unit = target.run {
		@Suppress("ktNoinlineFunc")
		fun DependencyResolveDetails.useExtraVersion(key: String): Unit = useVersion(extra["$key.version"] as String)

		configurations.all {
			resolutionStrategy {
				failOnVersionConflict()
				preferProjectModules()

				eachDependency {
					when (requested.group) {
						"io.github.microutils"             -> useExtraVersion("microutils-logging")
						"org.slf4j"                        -> useExtraVersion("slf4j")
						"ch.qos.logback"                   -> useExtraVersion("logback")
						"io.kotest"                        -> useExtraVersion("kotest")
						"io.mockk"                         -> useExtraVersion("mockk")
						"io.github.serpro69"               -> useExtraVersion("kotlin-faker")
						"commons-codec"                    -> useExtraVersion("commons-codec")
						"io.ktor"                          -> useExtraVersion("ktor")
						"org.koin"                         -> useExtraVersion("koin")
						"org.jetbrains.kotlin"             -> useExtraVersion("kotlin")
						"org.jetbrains.exposed"            -> useExtraVersion("exposed")
						"org.jetbrains.kotlinx"            -> when {
							requested.name.startsWith("kotlinx-coroutines")    -> useExtraVersion("kotlinx-coroutines")
							requested.name.startsWith("kotlinx-serialization") -> useExtraVersion("kotlinx-serialization")
							requested.name.startsWith("kotlinx-html")          -> useExtraVersion("kotlinx-html")
							requested.name.startsWith("kotlinx-datetime")      -> useExtraVersion("kotlinx-datetime")
						}
						"org.webjars"                      -> when (requested.name) {
							"swagger-ui" -> useExtraVersion("webjars-swagger")
						}

						"com.fasterxml.jackson"            -> useExtraVersion("jackson")
						"com.fasterxml.jackson.core"       -> useExtraVersion("jackson")
						"com.fasterxml.jackson.datatype"   -> useExtraVersion("jackson")
						"com.fasterxml.jackson.module"     -> useExtraVersion("jackson")
						"com.fasterxml.jackson.dataformat" -> useExtraVersion("jackson")
					}
				}
			}
		}
	}

	private fun configureDependencies(target: Project): Unit = target.run {
		dependencies {
			"implementation"("org.jetbrains.kotlinx:kotlinx-datetime")

			"testImplementation"("ch.qos.logback:logback-classic")

			"testImplementation"("io.kotest:kotest-runner-junit5")
			"testImplementation"("io.kotest:kotest-extensions-junit5")
			"testImplementation"("io.kotest:kotest-property")
			"testImplementation"("io.kotest:kotest-assertions-core")
			"testImplementation"("io.kotest:kotest-assertions-kotlinx-time")
			"testImplementation"("io.kotest:kotest-assertions-json")
			"testImplementation"("io.github.serpro69:kotlin-faker")
			"testImplementation"("io.mockk:mockk")
		}
	}
}
