configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("spring-boot-library-plugin") {
			id = "ru.itbasis.gradle.backend-spring-boot-library-plugin"
			implementationClass = "ru.itbasis.gradle.backend.springboot.BackendLibraryPlugin"
		}
		register("spring-boot-library-db-plugin") {
			id = "ru.itbasis.gradle.backend-spring-boot-library-db-plugin"
			implementationClass = "ru.itbasis.gradle.backend.springboot.BackendLibraryDatabasePlugin"
		}
	}
}

dependencies {
	api(project(":backend-plugins:backend-spring-boot:backend-spring-boot-base-plugin"))

	api(kotlin("noarg"))
}
