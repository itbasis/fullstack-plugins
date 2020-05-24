package ru.itbasis.gradle.backend.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.the
import org.gradle.plugins.ide.idea.model.IdeaModel

class ConfigureSourceSetAction : Action<Project> {
	override fun execute(project: Project): Unit = project.run {
		configure<SourceSetContainer> {
			val mainSourceSet = this.getAt("main")

			create("itest") {
				compileClasspath += mainSourceSet.output
				runtimeClasspath += mainSourceSet.output
			}
		}
		val itestSourceSet = the<SourceSetContainer>().getAt("itest")

		configurations["itestImplementation"].extendsFrom(configurations["testImplementation"])
		configurations["itestCompile"].extendsFrom(configurations["testCompile"])
		configurations["itestRuntimeOnly"].extendsFrom(configurations["testRuntimeOnly"])

		configure<IdeaModel> {
			module {
				testSourceDirs = testSourceDirs + itestSourceSet.allJava.srcDirs
				testResourceDirs = testResourceDirs + itestSourceSet.resources.srcDirs
			}
		}
	}
}
