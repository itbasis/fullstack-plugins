package ru.itbasis.gradle.fullstack.common.kotlin.actions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.the
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation.Companion.MAIN_COMPILATION_NAME
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation.Companion.TEST_COMPILATION_NAME
import org.jetbrains.kotlin.gradle.utils.COMPILE
import org.jetbrains.kotlin.gradle.utils.IMPLEMENTATION
import org.jetbrains.kotlin.gradle.utils.RUNTIME_ONLY
import ru.itbasis.gradle.fullstack.common.kotlin.INTEGRATION_TEST

class ConfigureSourceSetAction : Action<Project> {
	override fun execute(target: Project): Unit = target.run {
		configure<SourceSetContainer> {
			val mainSourceSet = this.getAt(MAIN_COMPILATION_NAME)

			create(INTEGRATION_TEST) {
				compileClasspath += mainSourceSet.output
				runtimeClasspath += mainSourceSet.output
			}
		}
		val integrationTestSourceSet = the<SourceSetContainer>().getAt(INTEGRATION_TEST)

		listOf(IMPLEMENTATION, COMPILE, RUNTIME_ONLY).map {
			it.capitalize()
		}.forEach {
			configurations["$INTEGRATION_TEST$it"].extendsFrom(configurations["$TEST_COMPILATION_NAME$it"])
		}

		configure<IdeaModel> {
			module {
				testSourceDirs = testSourceDirs + integrationTestSourceSet.allJava.srcDirs
				testResourceDirs = testResourceDirs + integrationTestSourceSet.resources.srcDirs
			}
		}
	}
}
