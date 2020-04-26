configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("plugin") {
			id = "ru.itbasis.gradle.backend-spring-boot-service-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.service.BackendServicePlugin"
		}
	}
}

dependencies {
	api(project(":backend-plugins:backend-spring-boot:backend-spring-boot-base-plugin"))
}
