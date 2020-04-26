@file:Suppress("UnstableApiUsage")

package ru.itbasis.gradle.common.kotlin

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

class BackendCommonPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<KotlinPluginWrapper>()

		val javaVersion = JavaVersion.current()

		configure<JavaPluginConvention> {
			sourceCompatibility = javaVersion
			targetCompatibility = javaVersion
		}

		dependencies {
			"api"(kotlin("stdlib-jdk8"))
		}

		tasks {
			withType(KotlinJvmCompile::class) {
				kotlinOptions {
					jvmTarget = (when {
						javaVersion.isJava12Compatible -> JavaVersion.VERSION_12
						else -> JavaVersion.VERSION_1_8
					}).toString()

					@Suppress("SpellCheckingInspection")
					freeCompilerArgs = freeCompilerArgs + listOf("-Xjsr305=strict", "-Xuse-experimental=kotlin.Experimental")
				}
			}
		}

	}
}
