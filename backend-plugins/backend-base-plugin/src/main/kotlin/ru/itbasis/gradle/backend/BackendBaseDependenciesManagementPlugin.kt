package ru.itbasis.gradle.backend

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolveDetails
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.the
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class BackendBaseDependenciesManagementPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<DependencyManagementPlugin>()

		repositories {
			jcenter()
//			maven(url = "https://kotlin.bintray.com/kotlinx")
		}

		mapOf(
			"microutils.version" to "1.7.9",
			"javafaker.version" to "1.0.2",
			"kotest.version" to "4.0.2",
			"korlibs.klock.version" to "1.9.1",
			"liquibase.version" to "3.8.9",
			"jetbrains.exposed.version" to "0.23.1",
			"ktor.version" to "1.3.2",
			"koin.version" to "2.1.5",
			"kotlinx-html.version" to "0.7.1",
			"webjars-swagger.version" to "3.25.0"
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
						"org.webjars" -> when(requested.name){
							"swagger-ui" -> useExtraVersion("webjars-swagger")
						}
					}
				}
			}
		}
	}
}
