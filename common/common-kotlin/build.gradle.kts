plugins {
	`kotlin-dsl`
}

configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("backend-common-plugin") {
			id = "ru.itbasis.gradle.backend-common-plugin"
			implementationClass = "ru.itbasis.gradle.common.kotlin.BackendCommonPlugin"
		}
	}
}

val gradleDetektVersion = extra["gradle-detekt.version"] as String

dependencies {
	api(kotlin("gradle-plugin"))

	api(project(":common:common-core"))

	api("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$gradleDetektVersion")

	testImplementation(project(":common:common-tests"))
}
