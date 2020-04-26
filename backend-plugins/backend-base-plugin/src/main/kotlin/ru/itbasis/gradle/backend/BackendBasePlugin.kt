@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.backend

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.maybeCreate
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.gradle.language.base.plugins.LifecycleBasePlugin.ASSEMBLE_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.BUILD_GROUP
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import ru.itbasis.gradle.common.ide.idea.gradleRunConfiguration
import ru.itbasis.gradle.common.kotlin.CheckstylePlugin

class BackendBasePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<KotlinPluginWrapper>()
		apply<IdeaPlugin>()
		apply<CheckstylePlugin>()

		configureSourceSets(target = target)
		configureAssembleBackendTasks(target = target)
		configureIntegrationTestTasks(target = target)
		configureDependencies(target = target)
	}

	private fun configureSourceSets(target: Project): Unit = target.run {
		configure<SourceSetContainer> {
			val mainSourceSet = this.getAt("main")

			create("itest") {
				compileClasspath += mainSourceSet.output
				runtimeClasspath += mainSourceSet.output
			}
		}
		val itestSourceSet = the<SourceSetContainer>().getAt("itest")

		configurations["itestImplementation"].extendsFrom(configurations["testImplementation"])
		configurations["itestRuntimeOnly"].extendsFrom(configurations["testRuntimeOnly"])

		configure<IdeaModel> {
			module {
				testSourceDirs = testSourceDirs + itestSourceSet.allJava.srcDirs
				testResourceDirs = testResourceDirs + itestSourceSet.resources.srcDirs
			}
		}
	}

	private fun configureAssembleBackendTasks(target: Project): Unit = target.run {
		tasks {
			maybeCreate(ASSEMBLE_BACKEND_TASK_NAME).apply {
				group = BUILD_GROUP
				dependsOn(ASSEMBLE_TASK_NAME)
			}
		}
		rootProject.gradleRunConfiguration(cfgSubName = "assemble (backend only)", tasks = listOf(ASSEMBLE_BACKEND_TASK_NAME))
	}

	private fun configureIntegrationTestTasks(target: Project): Unit = target.run {
		val itestSourceSet = the<SourceSetContainer>().getAt("itest")

		tasks {
			val test by named(JavaPlugin.TEST_TASK_NAME, Test::class)

			val integrationTest = maybeCreate(INTEGRATION_TEST_TASK_NAME, Test::class).apply {
				description = "Runs integration tests."
				group = LifecycleBasePlugin.VERIFICATION_GROUP

				testClassesDirs = itestSourceSet.output.classesDirs
				classpath = itestSourceSet.runtimeClasspath
				shouldRunAfter(test)
			}

			named(CHECK_TASK_NAME) {
				dependsOn(integrationTest)
			}

			withType(Test::class) {
				useJUnitPlatform()
				maxParallelForks = Runtime.getRuntime().availableProcessors() / 2

				testLogging {
					showStandardStreams = true
					showExceptions = true
					events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED)
					exceptionFormat = TestExceptionFormat.FULL
				}
				environment("ROOT_DIR", rootDir.absolutePath)
			}
		}
	}

	private fun configureDependencies(target: Project): Unit = target.run {
		apply<BackendBaseDependenciesManagementPlugin>()

		dependencies {
			"testImplementation"(kotlin("test"))

			"testImplementation"("ch.qos.logback:logback-classic")

			"testImplementation"("io.kotest:kotest-runner-junit5")
			"testImplementation"("io.kotest:kotest-assertions-core")
			"testImplementation"("io.kotest:kotest-assertions-json")
			"testImplementation"("com.github.javafaker:javafaker")
		}
	}

	companion object {
		const val INTEGRATION_TEST_TASK_NAME = "integrationTest"
		const val ASSEMBLE_BACKEND_TASK_NAME = "assembleBackend"
	}
}
