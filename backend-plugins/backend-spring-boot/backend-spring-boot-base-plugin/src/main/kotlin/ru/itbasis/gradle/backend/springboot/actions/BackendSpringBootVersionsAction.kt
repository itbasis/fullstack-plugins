package ru.itbasis.gradle.backend.springboot.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.exclude
import ru.itbasis.gradle.common.core.useEmbeddedTomcat

class BackendSpringBootVersionsAction : Action<Project> {
	override fun execute(project: Project): Unit = project.run {
		val useEmbeddedTomcat = project.useEmbeddedTomcat()

		configurations.all {
			if (!useEmbeddedTomcat) {
				exclude(module = "spring-boot-starter-tomcat")
				exclude(group = "org.apache.tomcat.embed")
			}
		}
	}
}
