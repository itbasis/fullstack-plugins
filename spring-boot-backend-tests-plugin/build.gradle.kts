plugins {
	`kotlin-dsl`
	`maven-publish`
	id("com.gradle.plugin-publish")
}

pluginBundle {
	tags = listOf("ru.itbasis", "kotlin", "spring", "springframework", "spring-boot", "testing", "integration-test", "junit5", "kotest", "detekt")
}

gradlePlugin {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-tests-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.tests.BackendTestsPlugin"
		}
	}
}

dependencies {
	api(project(":spring-boot-backend-versions-plugin"))

	api("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:+")

//	api("io.kotlintest:kotlintest-gradle-plugin:+")
}
