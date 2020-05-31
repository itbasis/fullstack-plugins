plugins {
	kotlin("jvm")
	`kotlin-dsl`
}

dependencies {
	api(gradleTestKit())
	api("io.kotest:kotest-runner-junit5")
	api("io.kotest:kotest-runner-console")
	api("io.kotest:kotest-assertions-core")

	api(project(":root-module-plugin"))
}
