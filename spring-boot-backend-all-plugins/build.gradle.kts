plugins {
	`kotlin-dsl`
	`maven-publish`
}

dependencies {
	api(project(":spring-boot-backend-base-plugin"))

	api(project(":spring-boot-backend-library-plugin"))
	api(project(":spring-boot-backend-library-db-plugin"))

	api(project(":spring-boot-backend-service-plugin"))

	api(project(":spring-boot-backend-tests-plugin"))
	api(project(":spring-boot-backend-versions-plugin"))

	api(project(":root-module-plugin"))
}
