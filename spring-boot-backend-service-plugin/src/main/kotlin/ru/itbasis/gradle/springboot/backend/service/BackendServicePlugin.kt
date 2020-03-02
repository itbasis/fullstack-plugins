package ru.itbasis.gradle.springboot.backend.service

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOOT_JAR_TASK_NAME
import org.springframework.boot.gradle.tasks.bundling.BootJar
import ru.itbasis.gradle.springboot.backend.base.BackendBasePlugin

class BackendServicePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendBasePlugin>()

		tasks.named(BOOT_JAR_TASK_NAME, BootJar::class.java) {
			archiveBaseName.set("service")
		}

		dependencies {
			"implementation"("org.springframework.boot:spring-boot-starter-web")
			"implementation"("org.springframework.boot:spring-boot-starter-undertow")
		}
	}
}
