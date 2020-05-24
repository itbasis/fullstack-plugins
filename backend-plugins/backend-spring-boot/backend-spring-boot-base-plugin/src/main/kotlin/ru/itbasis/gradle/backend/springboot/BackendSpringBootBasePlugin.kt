@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.backend.springboot

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPlugin.JAR_TASK_NAME
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.allopen.gradle.SpringGradleSubplugin
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin
import org.springframework.boot.gradle.dsl.SpringBootExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOOT_JAR_TASK_NAME
import ru.itbasis.gradle.INTEGRATION_TEST_TASK_NAME
import ru.itbasis.gradle.backend.BackendBasePlugin
import ru.itbasis.gradle.backend.springboot.actions.BackendSpringBootVersionsAction

class BackendSpringBootBasePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendBasePlugin>()

		apply<SpringBootPlugin>()

		apply<SpringGradleSubplugin>()

		apply<Kapt3GradleSubplugin>()

		configure<SpringBootExtension> {
			buildInfo()
		}

		configureTasks(target = target)
		configureDependencies(target = target)

		listOf(BackendSpringBootVersionsAction()).forEach {
			it.execute(target)
		}
	}

	private fun configureTasks(target: Project): Unit = target.run {
		tasks {
			named(JAR_TASK_NAME, Jar::class) {
				enabled = true
			}

			named(BOOT_JAR_TASK_NAME) {
				enabled = false
			}

			named(JavaPlugin.TEST_TASK_NAME, Test::class) {
				environment("SPRING_PROFILES_ACTIVE", "test")
			}

			named(INTEGRATION_TEST_TASK_NAME, Test::class) {
				environment("SPRING_PROFILES_ACTIVE", "itest")
			}
		}
	}

	private fun configureDependencies(target: Project): Unit = target.run {
		dependencies {
			"api"("org.springframework.boot:spring-boot-starter")
			"kapt"("org.springframework.boot:spring-boot-configuration-processor")

			"testImplementation"("io.kotest:kotest-extensions-spring")

			"testImplementation"("org.springframework.boot:spring-boot-starter-test") {
				exclude(group = "org.junit.vintage")
			}
		}
	}

}
