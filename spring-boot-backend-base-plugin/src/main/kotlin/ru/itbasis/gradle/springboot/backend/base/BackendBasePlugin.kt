@file:Suppress("UnstableApiUsage", "ktNoinlineFunc")

package ru.itbasis.gradle.springboot.backend.base

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.allopen.gradle.SpringGradleSubplugin
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin
import org.springframework.boot.gradle.dsl.SpringBootExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar
import ru.itbasis.gradle.springboot.backend.tests.BackendTestsPlugin
import ru.itbasis.gradle.springboot.backend.versions.BackendVersionsPlugin

class BackendBasePlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendTestsPlugin>()
		apply<BackendVersionsPlugin>()

		apply<SpringBootPlugin>()

		apply<SpringGradleSubplugin>()

		apply<Kapt3GradleSubplugin>()

		configure<SpringBootExtension> {
			buildInfo()
		}

		val javaVersion = JavaVersion.current()

		configure<JavaPluginConvention> {
			sourceCompatibility = javaVersion
			targetCompatibility = javaVersion
		}

		dependencies {
			"api"(kotlin("stdlib-jdk8"))

			"api"("org.springframework.boot:spring-boot-starter")
			"kapt"("org.springframework.boot:spring-boot-configuration-processor")
		}

		tasks {
			named(JavaPlugin.JAR_TASK_NAME, Jar::class) {
				enabled = true
			}

			named(SpringBootPlugin.BOOT_JAR_TASK_NAME, BootJar::class) {
				archiveClassifier.set("boot")
			}

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
