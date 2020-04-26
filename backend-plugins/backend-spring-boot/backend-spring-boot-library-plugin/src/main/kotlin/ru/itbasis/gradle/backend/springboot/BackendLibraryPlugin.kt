package ru.itbasis.gradle.backend.springboot

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class BackendLibraryPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendSpringBootBasePlugin>()

		dependencies {
			"api"("com.fasterxml.jackson.module:jackson-module-kotlin")

			"api"("io.github.microutils:kotlin-logging")

			"api"("com.soywiz.korlibs.klock:klock-jvm")
		}
	}
}
