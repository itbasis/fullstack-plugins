configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("backend-base-plugin") {
			id = "ru.itbasis.gradle.backend-base-plugin"
			implementationClass = "ru.itbasis.gradle.backend.BackendBasePlugin"
		}
	}
}

dependencies {
	api(kotlin("gradle-plugin"))

	api(project(":common:common-kotlin"))
	api(project(":common:common-ide-idea"))

	api("org.springframework.boot:spring-boot-gradle-plugin:2.3.0.RELEASE")
	api("io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE")
}
