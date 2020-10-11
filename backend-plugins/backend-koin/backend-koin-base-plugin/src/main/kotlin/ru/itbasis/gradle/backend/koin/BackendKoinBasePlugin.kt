package ru.itbasis.gradle.backend.koin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlinx.serialization.gradle.SerializationGradleSubplugin
import ru.itbasis.gradle.backend.AbstractPlugin
import ru.itbasis.gradle.backend.BackendBasePlugin

class BackendKoinBasePlugin : AbstractPlugin() {
	override fun apply(target: Project): Unit = target.run {
		apply<BackendBasePlugin>()
		apply<SerializationGradleSubplugin>()

		repositories {
			maven(url = "https://kotlin.bintray.com/ktor")
		}

		dependencies {
			"api"("io.github.microutils:kotlin-logging")

			"api"("org.jetbrains.kotlinx:kotlinx-serialization-json")

			"api"("org.koin:koin-core-ext")

			"testImplementation"("org.koin:koin-test")
			"testImplementation"("io.kotest:kotest-extensions-koin")
			"testImplementation"("io.kotest:kotest-assertions-ktor")
		}

		tasks {
			withType<KotlinJvmCompile> {
				kotlinOptions {
					freeCompilerArgs = freeCompilerArgs + listOf(
						"-Xuse-experimental=io.ktor.util.KtorExperimentalAPI",
						"-Xuse-experimental=io.ktor.locations.KtorExperimentalLocationsAPI",
						"-Xuse-experimental=kotlinx.serialization.ImplicitReflectionSerializer"
					)
				}
			}
		}
	}
}
