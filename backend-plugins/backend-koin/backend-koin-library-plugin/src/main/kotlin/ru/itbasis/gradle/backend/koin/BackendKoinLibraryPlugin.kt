package ru.itbasis.gradle.backend.koin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class BackendKoinLibraryPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendKoinBasePlugin>()

		dependencies {
			"api"("io.ktor:ktor-locations")

			"api"("io.ktor:ktor-jackson")
			"api"("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
		}
	}
}