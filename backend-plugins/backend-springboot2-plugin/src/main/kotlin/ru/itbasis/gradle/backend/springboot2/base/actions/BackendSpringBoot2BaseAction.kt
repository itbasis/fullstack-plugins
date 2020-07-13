package ru.itbasis.gradle.backend.springboot2.base.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin.JAR_TASK_NAME
import org.gradle.api.plugins.JavaPlugin.TEST_TASK_NAME
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.springframework.boot.gradle.dsl.SpringBootExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOOT_JAR_TASK_NAME
import org.springframework.boot.gradle.tasks.bundling.BootJar
import ru.itbasis.gradle.INTEGRATION_TEST_TASK_NAME
import ru.itbasis.gradle.backend.BackendBasePlugin

class BackendSpringBoot2BaseAction : Action<Project> {
	override fun execute(target: Project): Unit = target.run {
		apply<BackendBasePlugin>()

		if (plugins.hasPlugin(SpringBootPlugin::class)) {
			return@run
		}

		apply<SpringBootPlugin>()

		configure<SpringBootExtension> {
			buildInfo {
				// TODO Git commit hash
			}
		}


		tasks {
			named<Jar>(JAR_TASK_NAME) {
				enabled = true
			}
			named<BootJar>(BOOT_JAR_TASK_NAME) {
				layered()
				enabled = false
			}

			named<Test>(TEST_TASK_NAME) {
				environment("SPRING_PROFILES_ACTIVE", "test")
			}

			named<Test>(INTEGRATION_TEST_TASK_NAME) {
				environment("SPRING_PROFILES_ACTIVE", "itest")
			}
		}
	}
}
