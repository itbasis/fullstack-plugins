package ru.itbasis.gradle.rootmodule

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe
import org.gradle.testfixtures.ProjectBuilder
import org.jetbrains.gradle.ext.IdeaExtPlugin

class RootModulePluginTest : FunSpec(
	{
		test("using plugin") {
			val project = ProjectBuilder.builder().build()
			project.pluginManager.apply("ru.itbasis.root-module")

			project.plugins.getPlugin(RootModulePlugin::class.java) shouldNotBe null
			project.plugins.getPlugin(IdeaExtPlugin::class.java) shouldNotBe null
		}
	}
)
