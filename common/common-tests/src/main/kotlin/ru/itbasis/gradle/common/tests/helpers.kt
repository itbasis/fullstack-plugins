package ru.itbasis.gradle.common.tests

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.repositories
import org.gradle.testfixtures.ProjectBuilder
import ru.itbasis.gradle.rootmodule.RootModulePlugin

fun initTestProject(): Project {
	val project = ProjectBuilder.builder().build()
	project.repositories { jcenter() }
	project.pluginManager.apply(RootModulePlugin::class)
	return project
}

fun Project.getAllDependencies(filterPrefix: String = "") = configurations.asSequence().filter {
	it.name.startsWith(prefix = filterPrefix, ignoreCase = true)
		&& it.isCanBeResolved
		&& !it.name.endsWith("Metadata")
}.getAllDependencies()

fun Sequence<Configuration>.getAllDependencies() = map {
	it.resolvedConfiguration.resolvedArtifacts
}.flatten().distinct().map {
	it.id.componentIdentifier
}.filterIsInstance(ModuleComponentIdentifier::class.java).toList()
