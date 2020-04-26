package ru.itbasis.gradle.springboot.backend.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.contain
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe
import org.gradle.kotlin.dsl.extra
import ru.itbasis.gradle.common.tests.getAllDependencies
import ru.itbasis.gradle.common.tests.initTestProject

class BackendServicePluginTest : FunSpec(
	{
		test("checking Tomcat excluded dependencies (not set property)") {
			val project = initTestProject()

			project.pluginManager.apply("ru.itbasis.gradle.backend-spring-boot-service-plugin")
			project.plugins.getPlugin(BackendServicePlugin::class.java) shouldNotBe null

			val allDependencies = project.getAllDependencies()

			allDependencies.map { it.group } shouldNot contain("org.apache.tomcat.embed")

			val artifactIds = allDependencies.map { it.module }
			artifactIds shouldNotBe contain("spring-boot-starter-tomcat")
			artifactIds shouldBe contain("spring-boot-starter-undertow")
		}

		test("checking Tomcat excluded dependencies (set property)") {
			val project = initTestProject()
			project.extra["tomcat.enabled"] = false

			project.pluginManager.apply("ru.itbasis.gradle.backend-spring-boot-service-plugin")
			project.plugins.getPlugin(BackendServicePlugin::class.java) shouldNotBe null

			val allDependencies = project.getAllDependencies()

			allDependencies.map { it.group } shouldNot contain("org.apache.tomcat.embed")

			val artifactIds = allDependencies.map { it.module }
			artifactIds shouldNotBe contain("spring-boot-starter-tomcat")
			artifactIds shouldBe contain("spring-boot-starter-undertow")
		}

		test("checking Tomcat including dependencies") {
			val project = initTestProject()
			project.extra["tomcat.enabled"] = true

			project.pluginManager.apply("ru.itbasis.gradle.backend-spring-boot-service-plugin")
			project.plugins.getPlugin(BackendServicePlugin::class.java) shouldNotBe null

			val allDependencies = project.getAllDependencies()

			allDependencies.map { it.group } should contain("org.apache.tomcat.embed")

			val artifactIds = allDependencies.map { it.module }
			artifactIds shouldBe contain("spring-boot-starter-tomcat")
			artifactIds shouldNotBe contain("spring-boot-starter-undertow")
		}
	}
)
