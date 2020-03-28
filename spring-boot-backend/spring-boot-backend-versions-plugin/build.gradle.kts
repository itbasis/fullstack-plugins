configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-versions-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.versions.BackendVersionsPlugin"
		}
	}
}

dependencies {
	api(kotlin("gradle-plugin"))

	api("org.springframework.boot:spring-boot-gradle-plugin:2.2.6.RELEASE")
}
