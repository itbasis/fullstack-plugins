@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.common.kotlin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP
import ru.itbasis.gradle.common.kotlin.checkstyle.CheckstyleDetektAction
import ru.itbasis.gradle.common.kotlin.checkstyle.CheckstyleJacocoAction

class CheckstylePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendCommonPlugin>()

		tasks {
			register(CHECKSTYLE_TASK_NAME) {
				group = VERIFICATION_GROUP
			}

			named(CHECK_TASK_NAME) {
				dependsOn(CHECKSTYLE_TASK_NAME)
			}
		}

		listOf(CheckstyleDetektAction(), CheckstyleJacocoAction()).forEach {
			it.execute(target)
		}
	}

	companion object {
		const val CHECKSTYLE_TASK_NAME = "checkstyle"
	}
}
