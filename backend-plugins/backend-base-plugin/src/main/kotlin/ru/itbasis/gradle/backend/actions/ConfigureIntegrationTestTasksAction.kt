package ru.itbasis.gradle.backend.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.maybeCreate
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import ru.itbasis.gradle.INTEGRATION_TEST_TASK_NAME

class ConfigureIntegrationTestTasksAction : Action<Project> {
	override fun execute(project: Project): Unit = project.run {
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

}
