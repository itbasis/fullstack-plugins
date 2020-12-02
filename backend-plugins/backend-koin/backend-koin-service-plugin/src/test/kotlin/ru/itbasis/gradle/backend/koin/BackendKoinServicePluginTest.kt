package ru.itbasis.gradle.backend.koin

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.singleElement
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import org.gradle.kotlin.dsl.dependencies
import ru.itbasis.gradle.common.tests.getAllDependencies
import ru.itbasis.gradle.common.tests.initTestProject

class BackendKoinServicePluginTest : FunSpec(
	{
		test("checking dependencies") {
			val project = initTestProject()
			project.pluginManager.apply("ru.itbasis.gradle.backend-koin-service-plugin")

			project.plugins.getPlugin(BackendKoinBasePlugin::class.java) shouldNotBe null
			project.plugins.getPlugin(BackendKoinServicePlugin::class.java) shouldNotBe null

			project.dependencies {
//				"api"("org.jetbrains.kotlinx:kotlinx-html-js")
				"api"("org.jetbrains.kotlinx:kotlinx-html-jvm")
			}

			val allDependencies = project.getAllDependencies()

			allDependencies.filter {
				it.group == "io.ktor"
			}.map {
				it.version
			}.toSet() should singleElement("1.4.3")

			allDependencies.filter {
//				it.module == "kotlinx-html-js"
				it.module == "kotlinx-html-jvm"
			}.map {
				it.version
			} should singleElement("0.7.2")
		}

		test("fatJar") {
			val project = initTestProject()
			project.pluginManager.apply("ru.itbasis.gradle.backend-koin-service-plugin")

			val fatJar = project.tasks.findByName("fatJar")

			fatJar shouldNotBe null
		}
	}
)
