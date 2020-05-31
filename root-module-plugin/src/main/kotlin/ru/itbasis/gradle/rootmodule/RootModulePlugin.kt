package ru.itbasis.gradle.rootmodule

import com.gemnasium.GemnasiumGradlePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.withType
import ru.itbasis.gradle.common.ide.idea.gradleRunConfiguration

@Suppress("unused")
class RootModulePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		check(target == rootProject) {
			"The plugin can only be used in the root project."
		}

		apply<IdeaModuleRootPlugin>()

		tasks.withType(Wrapper::class) {
			distributionType = Wrapper.DistributionType.ALL
		}

		allprojects {
			apply<GemnasiumGradlePlugin>()
		}

		gradleRunConfiguration(tasks = listOf("clean"))
		gradleRunConfiguration(cfgSubName = "assemble", tasks = listOf("clean", "assemble"))
		gradleRunConfiguration(cfgSubName = "all tests", tasks = listOf("assemble", "check")) {
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
