configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("backend-koin-service-plugin") {
			id = "ru.itbasis.gradle.backend-koin-service-plugin"
			implementationClass = "ru.itbasis.gradle.backend.koin.BackendKoinServicePlugin"
		}
	}
}

val shadowVersion = extra["gradle-shadow.version"] as String

dependencies {
	api(project(":backend-plugins:backend-koin:backend-koin-library-plugin"))

	api("com.github.jengelman.gradle.plugins:shadow:$shadowVersion")
}
