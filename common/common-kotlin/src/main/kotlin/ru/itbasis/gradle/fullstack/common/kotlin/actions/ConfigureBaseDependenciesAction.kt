package ru.itbasis.gradle.fullstack.common.kotlin.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.itbasis.gradle.fullstack.common.core.Libs

class ConfigureBaseDependenciesAction : Action<Project> {
	override fun execute(target: Project): Unit = target.run {
		configureResolutionStrategy(target = project)
		configureDependencies(target = project)
	}

	@Suppress("ComplexMethod")
	@Deprecated(message = "replace to VERSION_CATALOGS")
	private fun configureResolutionStrategy(target: Project): Unit = target.run {
		configurations.all {
			resolutionStrategy {
				failOnVersionConflict()
				preferProjectModules()

				eachDependency {
					when (requested.group) {
						"io.github.microutils"             -> useVersion("2.0.5")
						"org.slf4j"                        -> useVersion("1.7.30")
						"ch.qos.logback"                   -> useVersion("1.2.3")

						"io.kotest"                        -> useVersion("4.5.0")
						"io.kotest.extensions"             -> when {
							requested.name.startsWith("kotest-assertions-kotlinx-datetime") -> useVersion("1.0.0")
							requested.name.startsWith("kotest-assertions-ktor")             -> useVersion("1.0.3")
						}

						"io.mockk"                         -> useVersion("1.11.0")
						"io.github.serpro69"               -> useVersion("1.7.1")
						"commons-codec"                    -> useVersion("1.15")
						"io.ktor"                          -> useVersion("1.5.4")
						"org.koin"                         -> useVersion("3.0.1")
						"org.jetbrains.kotlin"             -> useVersion(Libs.kotlinVersion)
						"org.jetbrains.exposed"            -> useVersion("0.31.1")
						"org.jetbrains.kotlinx"            -> when {
							requested.name.startsWith("kotlinx-coroutines")    -> useVersion("1.5.0")
							requested.name.startsWith("kotlinx-serialization") -> useVersion("1.2.1")
							requested.name.startsWith("kotlinx-html")          -> useVersion("0.7.3")
							requested.name.startsWith("kotlinx-datetime")      -> useVersion("0.2.0")
						}
						"org.webjars"                      -> when (requested.name) {
							"swagger-ui" -> useVersion("3.48.0")
						}

						"org.liquibase"                    -> useVersion("4.3.5")

						// TODO migrate to jackson-bom
						"com.fasterxml.jackson"            -> useVersion("2.12.3")
						"com.fasterxml.jackson.core"       -> useVersion("2.12.3")
						"com.fasterxml.jackson.datatype"   -> useVersion("2.12.3")
						"com.fasterxml.jackson.module"     -> useVersion("2.12.3")
						"com.fasterxml.jackson.dataformat" -> useVersion("2.12.3")
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
			"testImplementation"("io.kotest.extensions:kotest-assertions-kotlinx-datetime")
			"testImplementation"("io.kotest:kotest-assertions-json")

			"testImplementation"("io.github.serpro69:kotlin-faker")

			"testImplementation"("io.mockk:mockk")
		}
	}
}
