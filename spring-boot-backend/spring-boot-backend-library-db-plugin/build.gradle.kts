configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-library-db-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.library.BackendLibraryDatabasePlugin"
		}
	}
}

dependencies {
	api(project(":spring-boot-backend:spring-boot-backend-library-plugin"))

	api(kotlin("noarg"))
}
