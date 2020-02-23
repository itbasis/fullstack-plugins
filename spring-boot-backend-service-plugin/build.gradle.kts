plugins {
	`kotlin-dsl`
	`maven-publish`
}

gradlePlugin {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-service-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.service.BackendServicePlugin"
		}
	}
}

dependencies {
	api(project(":spring-boot-backend-base-plugin"))
}
