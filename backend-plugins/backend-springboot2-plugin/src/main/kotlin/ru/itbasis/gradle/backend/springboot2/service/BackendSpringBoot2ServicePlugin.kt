package ru.itbasis.gradle.backend.springboot2.service

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOOT_JAR_TASK_NAME
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun
import ru.itbasis.gradle.backend.AbstractPlugin
import ru.itbasis.gradle.backend.springboot2.base.actions.BackendSpringBoot2BaseAction
import ru.itbasis.gradle.backend.springboot2.base.actions.BackendSpringBoot2BaseDependenciesAction
import ru.itbasis.gradle.backend.springboot2.base.actions.BackendSpringBoot2KaptAction

class BackendSpringBoot2ServicePlugin : AbstractPlugin() {
	override fun apply(target: Project): Unit = target.run {
		listOf(
			BackendSpringBoot2BaseAction(),
			BackendSpringBoot2KaptAction(),
			BackendSpringBoot2BaseDependenciesAction()
			// TODO https://docs.spring.io/spring-boot/docs/2.4.0-M1/gradle-plugin/api/org/springframework/boot/gradle/tasks/bundling/BootBuildImage.html
			// https://docs.spring.io/spring-boot/docs/2.4.0-M1/gradle-plugin/reference/html/#build-image
		).forEach {
			it.execute(project)
		}

		dependencies {
			"implementation"("org.springframework.boot:spring-boot-starter-web")

			"implementation"("org.springframework.boot:spring-boot-starter-undertow")
			"implementation"("org.springframework.boot:spring-boot-starter-actuator")
		}

		tasks {
			named<BootJar>(BOOT_JAR_TASK_NAME) {
				enabled = true
				archiveBaseName.set("service")
				archiveClassifier.set("boot")
			}
			withType<BootRun> {
				sourceResources(project.the<SourceSetContainer>()["main"])
			}
		}
	}
}
