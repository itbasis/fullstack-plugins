package ru.itbasis.gradle.backend.springboot2.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.contain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe
import ru.itbasis.gradle.common.tests.getAllDependencies
import ru.itbasis.gradle.common.tests.initTestProject

class BackendSpringBoot2ServicePluginTest : FunSpec(
	{

		test("checking Tomcat excluded dependencies") {
			val project = initTestProject()

			project.pluginManager.apply("ru.itbasis.gradle.backend-springboot2-service-plugin")
			project.plugins.getPlugin(BackendSpringBoot2ServicePlugin::class.java) shouldNotBe null

			val allDependencies = project.getAllDependencies()

			allDependencies.map { it.group } shouldNot contain("org.apache.tomcat.embed")

			val artifactIds = allDependencies.map { it.module }
			artifactIds shouldNotBe contain("spring-boot-starter-tomcat")
			artifactIds shouldBe contain("spring-boot-starter-undertow")

		}
	}
)
