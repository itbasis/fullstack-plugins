package ru.itbasis.gradle.backend.koin

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.singleElement
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import ru.itbasis.gradle.common.tests.getAllDependencies
import ru.itbasis.gradle.common.tests.initTestProject

class BackendKoinBasePluginTest : FunSpec(
	{
		test("checking koin dependencies") {
			val project = initTestProject()
			project.pluginManager.apply("ru.itbasis.gradle.backend-koin-base-plugin")

			project.plugins.getPlugin(DependencyManagementPlugin::class.java) shouldNotBe null
			project.plugins.getPlugin(BackendKoinBasePlugin::class.java) shouldNotBe null

			project.getAllDependencies().filter {
				it.group == "org.koin"
			}.map {
				it.version
			}.toSet() should singleElement("2.2.0-rc-2")

			project.getAllDependencies(filterPrefix = "itest").map {
				"${it.group}:${it.module}"
			} shouldContain "org.koin:koin-test"
		}
	}
)
