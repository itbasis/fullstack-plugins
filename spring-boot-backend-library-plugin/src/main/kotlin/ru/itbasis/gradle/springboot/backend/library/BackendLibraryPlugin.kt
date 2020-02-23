package ru.itbasis.gradle.springboot.backend.library

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import ru.itbasis.gradle.springboot.backend.base.BackendBasePlugin

class BackendLibraryPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendBasePlugin>()

		dependencies {
			"api"("com.fasterxml.jackson.module:jackson-module-kotlin")

			"api"("io.github.microutils:kotlin-logging")

			"api"("com.soywiz.korlibs.klock:klock-jvm")
		}
	}
}
