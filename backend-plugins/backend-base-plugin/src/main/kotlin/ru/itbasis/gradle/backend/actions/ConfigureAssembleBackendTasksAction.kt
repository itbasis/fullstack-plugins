package ru.itbasis.gradle.backend.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.language.base.plugins.LifecycleBasePlugin.ASSEMBLE_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.BUILD_GROUP
import ru.itbasis.gradle.ASSEMBLE_BACKEND_TASK_NAME
import ru.itbasis.gradle.common.ide.idea.gradleRunConfiguration

class ConfigureAssembleBackendTasksAction : Action<Project> {
	override fun execute(project: Project): Unit = project.run {
		tasks {
			maybeCreate(ASSEMBLE_BACKEND_TASK_NAME).apply {
				group = BUILD_GROUP
				dependsOn(ASSEMBLE_TASK_NAME)
			}
		}
		rootProject.gradleRunConfiguration(cfgSubName = "assemble (backend only)", tasks = listOf(ASSEMBLE_BACKEND_TASK_NAME))
	}
}
