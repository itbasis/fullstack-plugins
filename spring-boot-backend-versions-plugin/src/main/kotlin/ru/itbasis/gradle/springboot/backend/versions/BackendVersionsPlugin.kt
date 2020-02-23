package ru.itbasis.gradle.springboot.backend.versions

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.the
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class BackendVersionsPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<DependencyManagementPlugin>()

		repositories {
			jcenter()
		}

		the<DependencyManagementExtension>().apply {
			imports {
				mavenBom(SpringBootPlugin.BOM_COORDINATES)
			}
		}

		configurations.all {
			exclude(module = "spring-boot-starter-tomcat")
			exclude(group = "org.apache.tomcat.embed")

			resolutionStrategy {
				failOnVersionConflict()
				preferProjectModules()

				eachDependency {
					when (requested.group) {
						"io.github.microutils" -> useVersion("1.7.8")
						"io.kotest" -> useVersion("4.0.0-+")
						"com.github.javafaker" -> useVersion("1.0.1")
						"org.objenesis" -> useVersion("3.1")
						"com.soywiz.korlibs.klock" -> useVersion("1.8.8")
					}
				}
			}
		}
	}
}
