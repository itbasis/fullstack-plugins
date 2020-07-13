package ru.itbasis.gradle.backend.springboot2.library

import org.gradle.api.Project
import ru.itbasis.gradle.backend.AbstractPlugin
import ru.itbasis.gradle.backend.springboot2.base.actions.BackendSpringBoot2BaseAction
import ru.itbasis.gradle.backend.springboot2.base.actions.BackendSpringBoot2BaseDependenciesAction
import ru.itbasis.gradle.backend.springboot2.base.actions.BackendSpringBoot2KaptAction

class BackendSpringBoot2LibraryPlugin : AbstractPlugin() {
	override fun apply(target: Project): Unit = target.run {
		listOf(
			BackendSpringBoot2BaseAction(),
			BackendSpringBoot2KaptAction(),
			BackendSpringBoot2BaseDependenciesAction()
		).forEach {
			it.execute(project)
		}

	}
}
