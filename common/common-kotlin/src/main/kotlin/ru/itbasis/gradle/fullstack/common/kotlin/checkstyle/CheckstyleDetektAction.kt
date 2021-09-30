package ru.itbasis.gradle.fullstack.common.kotlin.checkstyle

import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import ru.itbasis.gradle.fullstack.common.kotlin.CheckstylePlugin.Companion.CHECKSTYLE_TASK_NAME

class CheckstyleDetektAction : Action<Project> {
	override fun execute(target: Project): Unit = target.run {
		apply<DetektPlugin>()

		configure<DetektExtension> {
			config.setFrom(files(
				rootDir.resolve("config/detekt.yml").takeIf { it.isFile },
				projectDir.resolve("config/detekt.yml").takeIf { it.isFile }
			))
			parallel = true
			buildUponDefaultConfig = true
			ignoreFailures = false
			reports {
				html { enabled = false }
				txt { enabled = false }
			}
		}

		tasks {
			named(CHECKSTYLE_TASK_NAME) {
				dependsOn("detekt")
			}
		}
	}
}
