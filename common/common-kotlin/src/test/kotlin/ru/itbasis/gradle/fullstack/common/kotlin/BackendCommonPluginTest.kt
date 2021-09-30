package ru.itbasis.gradle.fullstack.common.kotlin

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldNotBe
import ru.itbasis.gradle.common.tests.getAllDependencies
import ru.itbasis.gradle.common.tests.initTestProject

class BackendCommonPluginTest : FunSpec(
	{
		test("using plugin") {
			val project = initTestProject()

			project.pluginManager.apply("ru.itbasis.gradle.backend-common-plugin")

			project.plugins.findPlugin(BackendCommonPlugin::class.java) shouldNotBe null

			val allDependencies = project.getAllDependencies()

			allDependencies.map {
				it.module
			}.shouldContain("kotlin-stdlib-jdk8")
			allDependencies.map {
				it.module
			}.shouldContain("kotlin-stdlib-jdk10")
		}
	}
)
