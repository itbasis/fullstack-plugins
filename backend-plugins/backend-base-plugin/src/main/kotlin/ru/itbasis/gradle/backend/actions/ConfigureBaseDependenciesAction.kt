package ru.itbasis.gradle.backend.actions

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolveDetails
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getPlugin
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class ConfigureBaseDependenciesAction : Action<Project> {
	override fun execute(project: Project): Unit = project.run {
		apply<DependencyManagementPlugin>()

		repositories {
			jcenter()
		}

		mapOf(
			"kotlin.version" to project.plugins.getPlugin(KotlinPluginWrapper::class).kotlinPluginVersion,
			"microutils.version" to "1.7.9",
			"javafaker.version" to "1.0.2",
			"kotest.version" to "4.0.5",
			"korlibs.klock.version" to "1.9.1",
			"liquibase.version" to "3.9.0",
			"jetbrains.exposed.version" to "0.25.1",
			"ktor.version" to "1.3.2",
			"koin.version" to "2.1.5",
			"kotlinx-html.version" to "0.7.1",
			"webjars-swagger.version" to "3.25.1"
		).filterKeys {
			!extra.has(it)
		}.forEach { (k, v) ->
			extra[k] = v
		}

		the<DependencyManagementExtension>().apply {
			imports {
				mavenBom(SpringBootPlugin.BOM_COORDINATES)
			}
		}

		fun DependencyResolveDetails.useExtraVersion(key: String): Unit = useVersion(extra["${key}.version"] as String)

		configurations.all {
			resolutionStrategy {
				failOnVersionConflict()
				preferProjectModules()

				eachDependency {
					when (requested.group) {
						"io.github.microutils"     -> useExtraVersion("microutils")
						"io.kotest"                -> useExtraVersion("kotest")
						"com.github.javafaker"     -> useExtraVersion("javafaker")
						"com.soywiz.korlibs.klock" -> useExtraVersion("korlibs.klock")
						"io.ktor"                  -> useExtraVersion("ktor")
						"org.koin"                 -> useExtraVersion("koin")
						"org.jetbrains.exposed"    -> useExtraVersion("jetbrains.exposed")
						"org.jetbrains.kotlinx"    -> when {
							requested.name.startsWith("kotlinx-html") -> useExtraVersion("kotlinx-html")
						}
						"org.webjars"              -> when (requested.name) {
							"swagger-ui" -> useExtraVersion("webjars-swagger")
						}
					}
				}
			}
		}

		dependencies {
			"testImplementation"(kotlin("test"))

			"testImplementation"("ch.qos.logback:logback-classic")

			"testImplementation"("io.kotest:kotest-runner-junit5")
			"testImplementation"("io.kotest:kotest-runner-console")
			"testImplementation"("io.kotest:kotest-property")
			"testImplementation"("io.kotest:kotest-assertions-core")
			"testImplementation"("io.kotest:kotest-assertions-json")
			"testImplementation"("io.kotest:kotest-assertions-klock")
			"testImplementation"("com.github.javafaker:javafaker")
		}
	}
}
