package ru.itbasis.gradle.rootmodule

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe
import org.gradle.testfixtures.ProjectBuilder
import org.jetbrains.gradle.ext.IdeaExtPlugin

class RootModulePluginTest : FunSpec(
	{
		test("using plugin") {
			val project = ProjectBuilder.builder().build()
			project.pluginManager.apply("ru.itbasis.gradle.root-module-plugin")

			project.plugins.findPlugin(RootModulePlugin::class.java) shouldNotBe null
			project.plugins.findPlugin(IdeaExtPlugin::class.java) shouldNotBe null
		}
	}
)
