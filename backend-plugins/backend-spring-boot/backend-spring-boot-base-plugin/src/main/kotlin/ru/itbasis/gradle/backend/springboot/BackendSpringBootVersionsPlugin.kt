package ru.itbasis.gradle.backend.springboot

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.the
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import ru.itbasis.gradle.backend.BackendBaseDependenciesManagementPlugin
import ru.itbasis.gradle.common.core.useEmbeddedTomcat

class BackendSpringBootVersionsPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendBaseDependenciesManagementPlugin>()
		apply<DependencyManagementPlugin>()

		the<DependencyManagementExtension>().apply {
			imports {
				mavenBom(SpringBootPlugin.BOM_COORDINATES)
			}
		}

		val useEmbeddedTomcat = target.useEmbeddedTomcat()

		configurations.all {
			if (!useEmbeddedTomcat) {
				exclude(module = "spring-boot-starter-tomcat")
				exclude(group = "org.apache.tomcat.embed")
			}
		}
	}
}
