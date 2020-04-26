@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.flutter.core

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import org.gradle.language.base.plugins.LifecycleBasePlugin.BUILD_GROUP
import org.gradle.language.base.plugins.LifecycleBasePlugin.CLEAN_TASK_NAME

class FlutterCorePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		tasks {
			register<Delete>(CLEAN_TASK_NAME) {
				group = BUILD_GROUP
				doFirst {
					buildDir.deleteRecursively()
				}
			}
		}
	}

	companion object {
		const val FLUTTER_GROUP = "flutter"
	}
}
