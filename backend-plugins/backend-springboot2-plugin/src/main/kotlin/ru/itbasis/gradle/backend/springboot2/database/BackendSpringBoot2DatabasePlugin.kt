package ru.itbasis.gradle.backend.springboot2.database

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.jetbrains.kotlin.noarg.gradle.KotlinJpaSubplugin
import ru.itbasis.gradle.backend.AbstractPlugin

class BackendSpringBoot2DatabasePlugin : AbstractPlugin() {
	override fun apply(target: Project): Unit = target.run {
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
