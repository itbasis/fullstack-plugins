package ru.itbasis.gradle.fullstack.common.kotlin.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.language.base.plugins.LifecycleBasePlugin.ASSEMBLE_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.BUILD_GROUP
import ru.itbasis.gradle.fullstack.common.ide.idea.gradleRunConfiguration
import ru.itbasis.gradle.fullstack.common.kotlin.ASSEMBLE_BACKEND_TASK_NAME

class ConfigureAssembleBackendTasksAction : Action<Project> {
	override fun execute(target: Project): Unit = target.run {
		tasks {
			maybeCreate(ASSEMBLE_BACKEND_TASK_NAME).apply {
				group = BUILD_GROUP
				dependsOn(ASSEMBLE_TASK_NAME)
			}
		}
		rootProject.gradleRunConfiguration(cfgSubName = "assemble (backend only)", tasks = listOf(ASSEMBLE_BACKEND_TASK_NAME))
	}
}
