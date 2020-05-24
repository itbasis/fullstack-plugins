@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.backend

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import ru.itbasis.gradle.backend.actions.ConfigureAssembleBackendTasksAction
import ru.itbasis.gradle.backend.actions.ConfigureBaseDependenciesAction
import ru.itbasis.gradle.backend.actions.ConfigureIntegrationTestTasksAction
import ru.itbasis.gradle.backend.actions.ConfigureSourceSetAction
import ru.itbasis.gradle.common.kotlin.CheckstylePlugin

class BackendBasePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<KotlinPluginWrapper>()
		apply<IdeaPlugin>()
		apply<CheckstylePlugin>()

		listOf(
			ConfigureSourceSetAction(),
			ConfigureAssembleBackendTasksAction(),
			ConfigureIntegrationTestTasksAction(),
			ConfigureBaseDependenciesAction()
		).forEach {
			it.execute(target)
		}
	}

}
