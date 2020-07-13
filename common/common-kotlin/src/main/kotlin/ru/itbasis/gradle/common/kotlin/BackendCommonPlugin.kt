@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.common.kotlin

import org.gradle.api.JavaVersion
import org.gradle.api.JavaVersion.VERSION_12
import org.gradle.api.JavaVersion.VERSION_13
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

class BackendCommonPlugin : Plugin<Project> {
	private val javaCompatibles = listOf(VERSION_13, VERSION_12)

	override fun apply(target: Project): Unit = target.run {
		apply<KotlinPluginWrapper>()
		apply<IdeaPlugin>()

		val javaVersion = JavaVersion.current()

		configure<JavaPluginConvention> {
			sourceCompatibility = javaVersion
			targetCompatibility = javaVersion
		}

		repositories {
			jcenter()
			maven(url = "http://dl.bintray.com/kotlin/kotlin-eap")
		}

		dependencies {
			"api"(kotlin("stdlib-jdk8"))
		}

		tasks {
			withType(KotlinJvmCompile::class) {
				kotlinOptions {
					jvmTarget = (javaCompatibles.firstOrNull { javaVersion.isCompatibleWith(it) } ?: VERSION_1_8).toString()

					@Suppress("SpellCheckingInspection")
					freeCompilerArgs = freeCompilerArgs + listOf("-Xjsr305=strict", "-Xuse-experimental=kotlin.Experimental")
				}
			}
		}

		configure<IdeaModel> {
			module {
				isDownloadJavadoc = false
				isDownloadSources = false
			}
		}

	}
}
