configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("backend-koin-base-plugin") {
			id = "ru.itbasis.gradle.backend-koin-base-plugin"
			implementationClass = "ru.itbasis.gradle.backend.koin.BackendKoinBasePlugin"
		}
	}
}

val koinVersion = project.extra["koin.version"] as String

dependencies {
	api(kotlin("serialization"))

	api(project(":backend-plugins:backend-base-plugin"))

	api("org.koin:koin-gradle-plugin:$koinVersion")
}
