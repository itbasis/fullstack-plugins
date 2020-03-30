@file:Suppress("UnstableApiUsage", "ktNoinlineFunc")

package ru.itbasis.gradle.springboot.backend.tests

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin.TEST_TASK_NAME
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import ru.itbasis.gradle.common.backend.CheckstylePlugin

class BackendTestsPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<KotlinPluginWrapper>()
		apply<CheckstylePlugin>()
		apply<IdeaPlugin>()

		configure<SourceSetContainer> {
			val mainSourceSet = this.getAt("main")

			create("itest") {
				compileClasspath += mainSourceSet.output
				runtimeClasspath += mainSourceSet.output
			}
		}
		val itestSourceSet = the<SourceSetContainer>().getAt("itest")

		configurations["itestImplementation"].extendsFrom(configurations["testImplementation"])
		configurations["itestRuntimeOnly"].extendsFrom(configurations["testRuntimeOnly"])

		configure<IdeaModel> {
			module {
				testSourceDirs = testSourceDirs + itestSourceSet.allJava.srcDirs
				testResourceDirs = testResourceDirs + itestSourceSet.resources.srcDirs
			}
		}

		tasks {
			val test by named(TEST_TASK_NAME, Test::class) {
				environment("SPRING_PROFILES_ACTIVE", "test")
			}

			val integrationTest by registering(Test::class) {
				description = "Runs integration tests."
				group = VERIFICATION_GROUP

				testClassesDirs = itestSourceSet.output.classesDirs
				classpath = itestSourceSet.runtimeClasspath
				shouldRunAfter(test)

				environment("SPRING_PROFILES_ACTIVE", "itest")
			}

			named(CHECK_TASK_NAME) {
				dependsOn(integrationTest)
			}

			withType(Test::class) {
				useJUnitPlatform()
				maxParallelForks = Runtime.getRuntime().availableProcessors() / 2

				testLogging {
					showStandardStreams = true
					showExceptions = true
					events = setOf(FAILED, PASSED)
					exceptionFormat = FULL
				}
				environment("ROOT_DIR", rootDir.absolutePath)
			}
		}

		dependencies {
			"testImplementation"(kotlin("test"))

			"testImplementation"("ch.qos.logback:logback-classic")

			"testImplementation"("io.kotest:kotest-runner-junit5")
			"testImplementation"("io.kotest:kotest-extensions-spring")
			"testImplementation"("io.kotest:kotest-assertions-json")
			"testImplementation"("com.github.javafaker:javafaker")

			"testImplementation"("org.springframework.boot:spring-boot-starter-test") {
				exclude(group = "org.junit.vintage")
			}
		}

	}
}
