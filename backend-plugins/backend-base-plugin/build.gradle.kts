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

	api("org.springframework.boot:spring-boot-gradle-plugin:2.2.6.RELEASE")

	//	api("io.kotlintest:kotlintest-gradle-plugin:+")

}
