package ru.itbasis.gradle.fullstack.common.kotlin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import ru.itbasis.gradle.fullstack.common.kotlin.actions.ConfigureBaseDependenciesAction
import ru.itbasis.gradle.fullstack.common.kotlin.actions.ConfigureIntegrationTestTasksAction
import ru.itbasis.gradle.fullstack.common.kotlin.actions.ConfigureSourceSetAction

class BackendBasePlugin : AbstractPlugin() {
	override fun apply(target: Project): Unit = target.run {
		apply<KotlinPluginWrapper>()
		apply<IdeaPlugin>()
//		apply<CheckstylePlugin>()

		listOf(
			ConfigureSourceSetAction(),
//			ConfigureAssembleBackendTasksAction(),
			ConfigureIntegrationTestTasksAction(),
			ConfigureBaseDependenciesAction()
		).forEach {
			it.execute(target)
		}
	}
}
