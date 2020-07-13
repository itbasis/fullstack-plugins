package ru.itbasis.gradle.backend.springboot2.base.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

class BackendSpringBoot2BaseDependenciesAction : Action<Project> {
	override fun execute(target: Project): Unit = target.run {
		dependencies {
			"api"("org.springframework.boot:spring-boot-starter")

			"api"("com.fasterxml.jackson.module:jackson-module-kotlin")
			"api"("io.github.microutils:kotlin-logging")
			"api"("com.soywiz.korlibs.klock:klock-jvm")

			"testImplementation"("io.kotest:kotest-extensions-spring")

			"testImplementation"("org.springframework.boot:spring-boot-starter-test") {
				exclude(group = "org.junit.vintage")
			}
		}

		configurations.all {
			exclude(module = "spring-boot-starter-tomcat")
			exclude(group = "org.apache.tomcat.embed")
		}
	}
}
