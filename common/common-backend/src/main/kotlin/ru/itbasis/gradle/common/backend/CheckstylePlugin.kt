@file:Suppress("UnstableApiUsage", "ktNoinlineFunc")

package ru.itbasis.gradle.common.backend

import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP

class CheckstylePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<DetektPlugin>()

		configure<DetektExtension> {
			config.setFrom(files(rootDir.resolve("config/detekt.yml")))
			parallel = true
			buildUponDefaultConfig = true
			ignoreFailures = false
			reports {
				html { enabled = false }
				txt { enabled = false }
			}
		}
		tasks {
			val checkstyle by registering {
				group = VERIFICATION_GROUP

				dependsOn("detekt")
			}

			named(CHECK_TASK_NAME) {
				dependsOn(checkstyle)
			}
		}

	}
}
