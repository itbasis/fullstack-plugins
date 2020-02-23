plugins {
	`kotlin-dsl`
	`maven-publish`
}

gradlePlugin {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-base-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.base.BackendBasePlugin"
		}
	}
}

val kotlinVersion = extra["kotlin.version"] as String

dependencies {
	api(project(":spring-boot-backend-tests-plugin"))
	api(project(":spring-boot-backend-versions-plugin"))

	api(kotlin("allopen", kotlinVersion))
}
