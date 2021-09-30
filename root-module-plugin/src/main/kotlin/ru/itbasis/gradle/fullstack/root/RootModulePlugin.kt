package ru.itbasis.gradle.fullstack.root

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import ru.itbasis.gradle.fullstack.common.ide.idea.gradleRunConfiguration
import ru.itbasis.gradle.rootmodule.IdeaModuleRootPlugin

@Suppress("unused")
class RootModulePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		require(target == rootProject){
			"Plugin for root project only"
		}

		apply<IdeaModuleRootPlugin>()

		tasks.withType(Wrapper::class) {
			distributionType = Wrapper.DistributionType.BIN
		}

		allprojects {
			repositories {
				mavenCentral()
			}
		}

		gradleRunConfiguration(tasks = listOf("clean"))
		gradleRunConfiguration(cfgSubName = "assemble", tasks = listOf("clean", "assemble"))
		gradleRunConfiguration(cfgSubName = "check", tasks = listOf("assemble", "check")) {
			scriptParameters = "--scan"
		}
		gradleRunConfiguration(cfgSubName = "checkstyle", tasks = listOf("checkstyle")) {
			scriptParameters = "--scan --rerun-tasks"
		}
		gradleRunConfiguration(cfgSubName = "wrapper, refresh dependencies", tasks = listOf("clean", "wrapper", "processResources")) {
			scriptParameters = "--refresh-dependencies"
		}
		gradleRunConfiguration(cfgSubName = "resources", tasks = listOf("processResources")) {
			scriptParameters = "--rerun-tasks"
		}
	}
}
