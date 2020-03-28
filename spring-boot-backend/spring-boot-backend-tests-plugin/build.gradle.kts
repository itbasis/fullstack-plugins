configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("plugin") {
			id = "ru.itbasis.spring-boot.backend-tests-plugin"
			implementationClass = "ru.itbasis.gradle.springboot.backend.tests.BackendTestsPlugin"
		}
	}
}

dependencies {
	api(project(":common:common-ide-idea"))
	api(project(":common:common-backend"))

	api(project(":spring-boot-backend:spring-boot-backend-versions-plugin"))

//	api("io.kotlintest:kotlintest-gradle-plugin:+")
}
