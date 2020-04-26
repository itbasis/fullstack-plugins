configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("backend-koin-base-plugin") {
			id = "ru.itbasis.gradle.backend-koin-base-plugin"
			implementationClass = "ru.itbasis.gradle.backend.koin.BackendKoinBasePlugin"
		}
	}
}

dependencies {
	api(project(":backend-plugins:backend-base-plugin"))
}
