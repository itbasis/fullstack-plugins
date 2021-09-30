package ru.itbasis.gradle.fullstack.common.kotlin

import org.gradle.api.JavaVersion
import org.gradle.api.JavaVersion.VERSION_15
import org.gradle.api.JavaVersion.VERSION_16
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

class BackendCommonPlugin : Plugin<Project> {

	override fun apply(target: Project): Unit = target.run {
		apply<KotlinPluginWrapper>()
		apply<IdeaPlugin>()

		val javaVersion = JavaVersion.current()
		val supportedJvm = JAVA_SUPPORTED.lastOrNull {
			javaVersion.isCompatibleWith(it)
		}
		checkNotNull(supportedJvm) {
			"the current JVM $javaVersion is not compatible. Use JVM versions compatible with versions: $JAVA_SUPPORTED"
		}

		configure<JavaPluginExtension> {
			sourceCompatibility = javaVersion
			targetCompatibility = javaVersion
		}

		dependencies {
			"api"(kotlin("stdlib-jdk8"))
		}

		tasks {
			withType(KotlinJvmCompile::class) {
				kotlinOptions {
					jvmTarget = (JAVA_SUPPORTED.firstOrNull { javaVersion.isCompatibleWith(it) } ?: MIN_JAVA_SUPPORTED).toString()

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

	companion object {
		val JAVA_SUPPORTED = setOf(VERSION_15, VERSION_16)

		val MIN_JAVA_SUPPORTED = JAVA_SUPPORTED.first()
	}
}
