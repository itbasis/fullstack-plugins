package ru.itbasis.gradle.backend.koin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import ru.itbasis.gradle.backend.AbstractPlugin

class BackendKoinLibraryDatabasePlugin : AbstractPlugin() {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendKoinLibraryPlugin>()

		dependencies {
			"api"("org.jetbrains.exposed:exposed-core")
			"api"("org.jetbrains.exposed:exposed-dao")
			"api"("org.jetbrains.exposed:exposed-jdbc")
			"api"("org.jetbrains.exposed:exposed-java-time")

			"api"("org.liquibase:liquibase-core")
			"api"("org.postgresql:postgresql")
			"api"("com.zaxxer:HikariCP")
			"api"("org.yaml:snakeyaml")
		}
	}
}
