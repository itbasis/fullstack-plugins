plugins {
	`kotlin-dsl`
	`maven-publish`
	id("com.gradle.plugin-publish")
}

pluginBundle {
	tags = listOf("ru.itbasis", "kotlin", "spring", "springframework", "spring-boot")
}

gradlePlugin {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-versions-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.versions.BackendVersionsPlugin"
		}
	}
}

val kotlinVersion = extra["kotlin.version"] as String

dependencies {
	api(kotlin("gradle-plugin", kotlinVersion))

	api("org.springframework.boot:spring-boot-gradle-plugin:2.2.+")
}
