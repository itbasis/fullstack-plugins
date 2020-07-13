package ru.itbasis.gradle.backend

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.utils.loadPropertyFromResources

abstract class AbstractPlugin : Plugin<Project> {
	val pluginVersion = loadPropertyFromResources("project.properties", "project.version")
}
