package ru.itbasis.gradle.fullstack.common.kotlin

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.itbasis.gradle.fullstack.common.kotlin.utils.loadPropertyFromResources

abstract class AbstractPlugin : Plugin<Project> {
	val pluginVersion by lazy { loadPropertyFromResources("project.properties", "project.version") }
}
