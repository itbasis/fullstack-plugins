package ru.itbasis.gradle.fullstack.common.kotlin.checkstyle

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.invoke
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoReport
import ru.itbasis.gradle.fullstack.common.kotlin.CheckstylePlugin.Companion.CHECKSTYLE_TASK_NAME

class CheckstyleJacocoAction : Action<Project> {
	override fun execute(target: Project): Unit = target.run {
		apply<JacocoPlugin>()

		tasks {
			val jacocoTestReport by getting(JacocoReport::class) {
				dependsOn("test")

				reports {
					csv.required.set(false)
				}
			}
			named("test") {
				finalizedBy(jacocoTestReport)
			}
			named(CHECKSTYLE_TASK_NAME) {
				dependsOn(jacocoTestReport)
			}
		}
	}
}
