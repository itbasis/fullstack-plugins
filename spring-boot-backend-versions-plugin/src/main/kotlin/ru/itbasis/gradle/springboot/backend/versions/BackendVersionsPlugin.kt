@file:Suppress("ktNoinlineFunc")

package ru.itbasis.gradle.springboot.backend.versions

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolveDetails
import org.gradle.kotlin.dsl.*
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class BackendVersionsPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<DependencyManagementPlugin>()

		repositories {
			jcenter()
		}

		mapOf(
			"microutils.version" to "1.+",
			"javafaker.version" to "1.+",
			"kotest.version" to "4.+",
			"korlibs.klock.version" to "1.+"
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
			exclude(module = "spring-boot-starter-tomcat")
			exclude(group = "org.apache.tomcat.embed")

			resolutionStrategy {
				failOnVersionConflict()
				preferProjectModules()

				eachDependency {
					when (requested.group) {
						"io.github.microutils" -> useExtraVersion("microutils")
						"io.kotest" -> useExtraVersion("kotest")
						"com.github.javafaker" -> useExtraVersion("javafaker")
						"com.soywiz.korlibs.klock" -> useExtraVersion("korlibs.klock")
					}
				}
			}
		}
	}
}
