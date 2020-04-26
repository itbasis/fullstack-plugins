package ru.itbasis.gradle.springboot.backend.service

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOOT_JAR_TASK_NAME
import org.springframework.boot.gradle.tasks.bundling.BootJar
import ru.itbasis.gradle.common.core.useEmbeddedTomcat
import ru.itbasis.gradle.backend.springboot.BackendSpringBootBasePlugin

@Suppress("unused")
class BackendServicePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendSpringBootBasePlugin>()

		tasks.named(BOOT_JAR_TASK_NAME, BootJar::class.java) {
			enabled = true
			archiveBaseName.set("service")
			archiveClassifier.set("boot")
		}

		dependencies {
			"implementation"("org.springframework.boot:spring-boot-starter-web")

			if (!target.useEmbeddedTomcat()) {
				"implementation"("org.springframework.boot:spring-boot-starter-undertow")
				"implementation"("org.springframework.boot:spring-boot-starter-actuator")
			}
		}
	}
}
