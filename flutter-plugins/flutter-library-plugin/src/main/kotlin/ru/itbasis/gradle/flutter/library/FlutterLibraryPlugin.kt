package ru.itbasis.gradle.flutter.library

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import ru.itbasis.gradle.flutter.core.FlutterCorePlugin

class FlutterLibraryPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<FlutterCorePlugin>()
	}
}
