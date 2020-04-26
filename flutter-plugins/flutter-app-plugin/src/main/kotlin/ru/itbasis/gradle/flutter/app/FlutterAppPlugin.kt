@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.flutter.app

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.language.base.plugins.LifecycleBasePlugin.ASSEMBLE_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.BUILD_GROUP
import org.gradle.language.base.plugins.LifecycleBasePlugin.BUILD_TASK_NAME
import ru.itbasis.gradle.flutter.core.FlutterCorePlugin
import ru.itbasis.gradle.flutter.core.FlutterCorePlugin.Companion.FLUTTER_GROUP

class FlutterAppPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<FlutterCorePlugin>()

		tasks {
			val buildFlutterWebAppDebug by creating(Exec::class) {
				group = FLUTTER_GROUP
				enabled = false

				workingDir = projectDir
				inputs.files(projectDir.resolve("libs"))

				executable = "flutter"
				args("build", "web")
			}

			val buildFlutterWebAppRelease by creating(Exec::class) {
				group = FLUTTER_GROUP
				enabled = false

				workingDir = projectDir
				inputs.files(projectDir.resolve("libs"))

				executable = "flutter"
				args("build", "web", "--release")
			}

			maybeCreate(ASSEMBLE_TASK_NAME).apply {
				group = BUILD_GROUP
				dependsOn(buildFlutterWebAppRelease)
			}

			maybeCreate(BUILD_TASK_NAME).apply {
				group = BUILD_GROUP
				dependsOn(buildFlutterWebAppDebug, buildFlutterWebAppRelease)
			}
		}
	}
}
