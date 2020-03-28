configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-base-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.base.BackendBasePlugin"
		}
	}
}

dependencies {
	api(project(":spring-boot-backend:spring-boot-backend-tests-plugin"))
	api(project(":spring-boot-backend:spring-boot-backend-versions-plugin"))

	api(kotlin("allopen"))
}
