@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.common.kotlin

import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP

class CheckstylePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendCommonPlugin>()
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
