plugins {
	`kotlin-dsl`
	`maven-publish`
}

gradlePlugin {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-library-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.library.BackendLibraryPlugin"
		}
	}
}

dependencies {
	api(project(":spring-boot-backend-base-plugin"))
}
