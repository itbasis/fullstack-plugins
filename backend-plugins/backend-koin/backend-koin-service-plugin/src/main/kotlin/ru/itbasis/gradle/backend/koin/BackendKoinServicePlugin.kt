package ru.itbasis.gradle.backend.koin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.ApplicationPluginConvention
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.attributes
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.language.base.plugins.LifecycleBasePlugin.ASSEMBLE_TASK_NAME
import ru.itbasis.gradle.common.ide.idea.gradleRunConfiguration

class BackendKoinServicePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendKoinBasePlugin>()
		apply<ApplicationPlugin>()

		val mainClassName = "io.ktor.server.cio.EngineMain"
		configure<ApplicationPluginConvention> {
			this.mainClassName = mainClassName
		}

		dependencies {
			"api"("ch.qos.logback:logback-classic")

			"api"("io.ktor:ktor-server-cio")
		}

		tasks {
			val fatJar by registering(Jar::class) {
				archiveBaseName.set("service")
				archiveClassifier.set("all")

				duplicatesStrategy = DuplicatesStrategy.EXCLUDE

				manifest {
					attributes("Main-Class" to mainClassName)
				}

				from(configurations["runtimeClasspath"].map { if (it.isDirectory) it else zipTree(it) })
				from((target.extensions["sourceSets"] as SourceSetContainer).named("main").get().output)

				configurations["compileClasspath"].allDependencies.filterIsInstance<ProjectDependency>().map {
					it.dependencyProject
				}.forEach {
					dependsOn("${it.path}:$ASSEMBLE_TASK_NAME")
				}

			}

			named(ASSEMBLE_TASK_NAME) { dependsOn(fatJar) }
		}

		gradleRunConfiguration(cfgSubName = "run", tasks = listOf("run"))

		rootProject.gradleRunConfiguration(cfgSubName = "run", tasks = listOf("run"))
	}
}
