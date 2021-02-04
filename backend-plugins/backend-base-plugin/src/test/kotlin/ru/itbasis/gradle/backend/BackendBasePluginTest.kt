package ru.itbasis.gradle.backend

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.singleElement
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import ru.itbasis.gradle.common.tests.getAllDependencies
import ru.itbasis.gradle.common.tests.initTestProject

class BackendBasePluginTest : FunSpec(
	{
		test("checking dependencies") {
			val project = initTestProject()
			project.pluginManager.apply("ru.itbasis.gradle.backend-base-plugin")

			project.plugins.getPlugin(BackendBasePlugin::class.java) shouldNotBe null

			val allDependencies = project.getAllDependencies()

			allDependencies.map {
				it.module
			}.shouldContain("kotlin-stdlib-jdk8")

			allDependencies.filter {
				it.group == "org.jetbrains.kotlin"
			}.map {
				it.version
			}.toSet() should singleElement("1.4.30")
		}
	}
)
