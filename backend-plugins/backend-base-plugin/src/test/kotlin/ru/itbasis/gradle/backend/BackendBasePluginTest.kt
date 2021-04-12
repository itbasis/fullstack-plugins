package ru.itbasis.gradle.backend

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.contain
import io.kotest.matchers.collections.singleElement
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import ru.itbasis.gradle.common.tests.getAllDependencies
import ru.itbasis.gradle.common.tests.getAllDependenciesAsRows
import ru.itbasis.gradle.common.tests.initTestProject

class BackendBasePluginTest : FunSpec(
	{
		test("checking dependencies") {
			val project = initTestProject()
			project.pluginManager.apply("ru.itbasis.gradle.backend-base-plugin")

			project.plugins.findPlugin(BackendBasePlugin::class.java) shouldNotBe null

			val allDependencies = project.getAllDependencies()
			project.getAllDependenciesAsRows()

			allDependencies.map {
				it.module
			} should contain("kotlin-stdlib-jdk8")
//			FIXME } should containAll("kotlin-stdlib-jdk8", "kotest-extensions-junit5")

			allDependencies.filter {
				it.group == "org.jetbrains.kotlin"
			}.map {
				it.version
			}.toSet() should singleElement("1.4.31")
		}
	}
)
