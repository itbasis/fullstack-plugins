package ru.itbasis.gradle.common.kotlin

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldNotBe
import org.gradle.kotlin.dsl.repositories
import org.gradle.testfixtures.ProjectBuilder
import ru.itbasis.gradle.common.tests.getAllDependencies

class BackendCommonPluginTest : FunSpec(
	{
		test("using plugin") {
			val project = ProjectBuilder.builder().build()
			project.repositories { jcenter() }
			project.pluginManager.apply("ru.itbasis.gradle.backend-common-plugin")

			project.plugins.getPlugin(BackendCommonPlugin::class.java) shouldNotBe null

			val allDependencies = project.getAllDependencies()

			allDependencies.map {
				it.module
			}.shouldContain("kotlin-stdlib-jdk8")
		}
	}
)
