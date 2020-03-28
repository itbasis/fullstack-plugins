configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-library-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.library.BackendLibraryPlugin"
		}
	}
}

dependencies {
	api(project(":spring-boot-backend:spring-boot-backend-base-plugin"))
}
