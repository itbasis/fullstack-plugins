configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-service-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.service.BackendServicePlugin"
		}
	}
}

dependencies {
	api(project(":spring-boot-backend:spring-boot-backend-base-plugin"))
}
