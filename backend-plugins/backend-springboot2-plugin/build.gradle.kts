import com.gradle.publish.PluginBundleExtension

plugins {
	`maven-publish`
}

configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("backend-springboot2-library-plugin") {
			id = "ru.itbasis.gradle.backend-springboot2-library-plugin"
			implementationClass = "ru.itbasis.gradle.backend.springboot2.library.BackendSpringBoot2LibraryPlugin"
		}
		register("backend-springboot2-database-plugin") {
			id = "ru.itbasis.gradle.backend-springboot2-database-plugin"
			implementationClass = "ru.itbasis.gradle.backend.springboot2.database.BackendSpringBoot2DatabasePlugin"
		}
		register("backend-springboot2-service-plugin") {
			id = "ru.itbasis.gradle.backend-springboot2-service-plugin"
			implementationClass = "ru.itbasis.gradle.backend.springboot2.service.BackendSpringBoot2ServicePlugin"
		}
	}
}

configure<PluginBundleExtension> {
	tags = listOf("ru.itbasis", "kotlin", "spring", "springframework", "spring-boot")
}

dependencies {
	api(project(":backend-plugins:backend-base-plugin"))

	api(kotlin("allopen"))
	api(kotlin("noarg"))
}
