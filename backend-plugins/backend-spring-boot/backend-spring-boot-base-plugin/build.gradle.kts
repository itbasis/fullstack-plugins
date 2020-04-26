configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("spring-boot-base-plugin") {
			id = "ru.itbasis.gradle.backend-spring-boot-base-plugin"
			implementationClass = "ru.itbasis.gradle.backend.springboot.BackendSpringBootBasePlugin"
		}
	}
}

dependencies {
	api(project(":backend-plugins:backend-base-plugin"))

	api(kotlin("allopen"))
}
