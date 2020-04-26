package ru.itbasis.gradle.backend.koin

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.ApplicationPluginConvention
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import ru.itbasis.gradle.common.ide.idea.gradleRunConfiguration

class BackendKoinServicePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendKoinBasePlugin>()
		apply<ApplicationPlugin>()

		configure<ApplicationPluginConvention> {
			mainClassName = "io.ktor.server.cio.EngineMain"
		}

		apply<ShadowPlugin>()

		dependencies {
			"api"("ch.qos.logback:logback-classic")

			"api"("io.ktor:ktor-server-cio")
		}

		tasks {
			withType<ShadowJar> {
				archiveBaseName.set("service")
			}
		}

		gradleRunConfiguration(tasks = listOf("runShadow"))

		rootProject.gradleRunConfiguration(tasks = listOf("runShadow"))
	}
}
