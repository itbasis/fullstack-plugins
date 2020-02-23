package ru.itbasis.gradle.springboot.backend.library

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.jetbrains.kotlin.allopen.gradle.SpringGradleSubplugin
import org.jetbrains.kotlin.noarg.gradle.KotlinJpaSubplugin

class BackendLibraryDatabasePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendLibraryPlugin>()
		apply<SpringGradleSubplugin>()
		apply<KotlinJpaSubplugin>()

		dependencies {
			"api"("org.springframework.boot:spring-boot-starter-data-jpa")

			"api"("org.postgresql:postgresql")
			"api"("org.liquibase:liquibase-core")
		}

		configure<AllOpenExtension> {
			annotation("javax.persistence.Entity")
			annotation("javax.persistence.Embeddable")
			annotation("javax.persistence.MappedSuperclass")
		}
	}
}
