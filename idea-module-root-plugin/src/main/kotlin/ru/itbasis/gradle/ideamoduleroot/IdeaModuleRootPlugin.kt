@file:Suppress("ktNoinlineFunc")

package ru.itbasis.gradle.ideamoduleroot

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.gradle.ext.*
import org.jetbrains.gradle.ext.EncodingConfiguration.BomPolicy.WITH_BOM_ON_WINDOWS

@Suppress("unused")
class IdeaModuleRootPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<IdeaExtPlugin>()

		configure<IdeaModel> {
			project {
				this as ExtensionAware

				configure<ProjectSettings> {
					this as ExtensionAware

					doNotDetectFrameworks(
						"AngularCLI", "django", "ejb", "Python", "google-appengine-python", "buildout-python", "gwt", "appengine-java", "javaeeApplication"
					)

					configure<EncodingConfiguration> {
						encoding = "UTF-8"
						bomPolicy = WITH_BOM_ON_WINDOWS
						properties {
							encoding = "UTF-8"
							transparentNativeToAsciiConversion = true
						}
					}

					configure<ActionDelegationConfig> {
						delegateBuildRunToGradle = true
						testRunner = ActionDelegationConfig.TestRunner.GRADLE
					}

					configure<IdeaCompilerConfiguration> {
						displayNotificationPopup = true
						autoShowFirstErrorInEditor = true
						parallelCompilation = true
						processHeapSize = 1024
						clearOutputDirectory = true
						rebuildModuleOnDependencyChange = true
						enableAutomake = true
						javac {
							preferTargetJDKCompiler = true
						}
					}
				}
			}
		}

		gradleRunConfiguration(tasks = listOf("clean"))
		gradleRunConfiguration(cfgSubName = "assemble", tasks = listOf("clean", "assemble"))
		gradleRunConfiguration(cfgSubName = "all tests", tasks = listOf("assemble", "check"))
		gradleRunConfiguration(cfgSubName = "wrapper, refresh dependencies", tasks = listOf("clean", "wrapper")) {
			scriptParameters = "--refresh-dependencies"
		}
		gradleRunConfiguration(cfgSubName = "resources", tasks = listOf("processResources")) {
			scriptParameters = "--rerun-tasks"
		}
	}
}
