package ru.itbasis.gradle.backend.springboot2.base.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.kotlin.allopen.gradle.SpringGradleSubplugin
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin

class BackendSpringBoot2KaptAction : Action<Project> {
	override fun execute(target: Project): Unit = target.run {
		apply<SpringGradleSubplugin>()
		apply<Kapt3GradleSubplugin>()
		apply<IdeaPlugin>()

		configure<IdeaModel> {
		}

		dependencies {
			"annotationProcessor"("org.springframework.boot:spring-boot-configuration-processor")
			"kapt"("org.springframework.boot:spring-boot-configuration-processor")
		}
	}
}
