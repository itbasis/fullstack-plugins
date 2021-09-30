package ru.itbasis.gradle.common.tests

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.kotlin.dsl.apply
import org.gradle.testfixtures.ProjectBuilder
import ru.itbasis.gradle.fullstack.root.RootModulePlugin

fun initTestProject(projectBuilderConfig: ProjectBuilder.() -> Unit = {}): Project {
	val projectBuilder = ProjectBuilder.builder()
	projectBuilder.apply(projectBuilderConfig)

	val project = projectBuilder.build()

	project.repositories.add(project.repositories.mavenCentral())

	project.pluginManager.apply(RootModulePlugin::class)
	return project
}

fun Project.getAllDependenciesAsRows(filterPrefix: String = "") = getAllDependencies(filterPrefix = filterPrefix).map {
	"${it.group}:${it.module}:${it.version}"
}

fun Project.getAllDependencies(filterPrefix: String = "") = configurations.asSequence().filter {
	it.name.startsWith(prefix = filterPrefix, ignoreCase = true) &&
		it.isCanBeResolved &&
		!it.name.endsWith("Metadata")
}.getAllDependencies()

fun Sequence<Configuration>.getAllDependencies() = map {
	println("it: $it")
	val cfg = it.resolvedConfiguration
	println("cfg: $cfg")
	cfg.resolvedArtifacts
}.flatten().distinct().map {
	it.id.componentIdentifier
}.filterIsInstance(ModuleComponentIdentifier::class.java).toList()
