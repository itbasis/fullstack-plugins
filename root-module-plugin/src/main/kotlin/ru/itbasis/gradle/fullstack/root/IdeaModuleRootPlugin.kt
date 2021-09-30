package ru.itbasis.gradle.rootmodule

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.gradle.ext.ActionDelegationConfig
import org.jetbrains.gradle.ext.ActionDelegationConfig.TestRunner
import org.jetbrains.gradle.ext.EncodingConfiguration
import org.jetbrains.gradle.ext.EncodingConfiguration.BomPolicy.WITH_BOM_ON_WINDOWS
import org.jetbrains.gradle.ext.IdeaCompilerConfiguration
import org.jetbrains.gradle.ext.IdeaExtPlugin
import org.jetbrains.gradle.ext.ProjectSettings
import ru.itbasis.gradle.fullstack.common.core.DEFAULT_PROJECT_ENCODING

class IdeaModuleRootPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		check(target == rootProject) {
			"The plugin can only be used in the root project."
		}

		apply<IdeaExtPlugin>()

		configure<IdeaModel> {
			project {
				this as ExtensionAware

				configure<ProjectSettings> {
					this as ExtensionAware

					doNotDetectFrameworks(
						"AngularCLI",
						"django",
						"ejb",
						"Python",
						"google-appengine-python",
						"buildout-python",
						"gwt",
						"appengine-java",
						"javaeeApplication"
					)

					configure<EncodingConfiguration> {
						encoding = DEFAULT_PROJECT_ENCODING
						bomPolicy = WITH_BOM_ON_WINDOWS
						properties {
							encoding = DEFAULT_PROJECT_ENCODING
							transparentNativeToAsciiConversion = true
						}
					}

					configure<ActionDelegationConfig> {
						delegateBuildRunToGradle = true
						testRunner = TestRunner.GRADLE
					}

					configure<IdeaCompilerConfiguration> {
						displayNotificationPopup = true
						autoShowFirstErrorInEditor = true
						parallelCompilation = true
						enableAutomake = true
						javac {
							preferTargetJDKCompiler = true
						}
					}
				}
			}
		}
	}
}
