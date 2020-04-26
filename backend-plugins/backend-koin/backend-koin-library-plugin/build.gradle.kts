configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("backend-koin-library-plugin") {
			id = "ru.itbasis.gradle.backend-koin-library-plugin"
			implementationClass = "ru.itbasis.gradle.backend.koin.BackendKoinLibraryPlugin"
		}
		register("backend-koin-library-db-plugin") {
			id = "ru.itbasis.gradle.backend-koin-library-db-plugin"
			implementationClass = "ru.itbasis.gradle.backend.koin.BackendKoinLibraryDatabasePlugin"
		}
		register("backend-koin-library-openapi-plugin") {
			id = "ru.itbasis.gradle.backend-koin-library-openapi-plugin"
			implementationClass = "ru.itbasis.gradle.backend.koin.BackendKoinLibraryOpenApiPlugin"
		}
	}
}

dependencies {
	api(project(":backend-plugins:backend-koin:backend-koin-base-plugin"))

	api("org.openapitools:openapi-generator-gradle-plugin:+")
}
