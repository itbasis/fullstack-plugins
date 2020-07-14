package ru.itbasis.gradle.backend.actions

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolveDetails
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.the
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import ru.itbasis.gradle.backend.utils.getAllPropertiesFromResources
import ru.itbasis.gradle.backend.utils.putExtraKeys

class ConfigureBaseDependenciesAction : Action<Project> {
	override fun execute(target: Project): Unit = target.run {
		apply<DependencyManagementPlugin>()

		putExtraKeys(this@ConfigureBaseDependenciesAction.getAllPropertiesFromResources(propFileName = "versions.properties"))

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
						"io.github.serpro69"       -> useExtraVersion("kotlin-faker")
						"com.soywiz.korlibs.klock" -> useExtraVersion("korlibs.klock")
						"commons-codec"            -> useExtraVersion("commons-codec")
						"io.ktor"                  -> useExtraVersion("ktor")
						"org.koin"                 -> useExtraVersion("koin")
						"org.jetbrains.kotlin"     -> useExtraVersion("kotlin")
						"org.jetbrains.exposed"    -> useExtraVersion("exposed")
						"org.jetbrains.kotlinx"    -> when {
							requested.name.startsWith("kotlinx-serialization") -> useExtraVersion("kotlinx-serialization")
							requested.name.startsWith("kotlinx-html")          -> useExtraVersion("kotlinx-html")
						}
						"org.webjars"              -> when (requested.name) {
							"swagger-ui" -> useExtraVersion("webjars-swagger")
						}
						"com.amazonaws.serverless" -> useExtraVersion("aws-serverless")
						"com.amazonaws"            -> when {
							requested.name.startsWith("aws-java-sdk-") -> useExtraVersion("aws-java-sdk")
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
			"testImplementation"("io.github.serpro69:kotlin-faker")
		}
	}
}
