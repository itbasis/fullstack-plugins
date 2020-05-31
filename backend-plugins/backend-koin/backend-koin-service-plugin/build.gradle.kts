configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("backend-koin-service-plugin") {
			id = "ru.itbasis.gradle.backend-koin-service-plugin"
			implementationClass = "ru.itbasis.gradle.backend.koin.BackendKoinServicePlugin"
		}
	}
}

dependencies {
	api(project(":backend-plugins:backend-koin:backend-koin-library-plugin"))
}
