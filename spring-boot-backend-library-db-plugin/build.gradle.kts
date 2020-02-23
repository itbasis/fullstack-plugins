plugins {
	`kotlin-dsl`
	`maven-publish`
}

gradlePlugin {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-library-db-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.library.BackendLibraryDatabasePlugin"
		}
	}
}

val kotlinVersion = extra["kotlin.version"] as String

dependencies {
	api(project(":spring-boot-backend-library-plugin"))

	api(kotlin("noarg", kotlinVersion))
}
