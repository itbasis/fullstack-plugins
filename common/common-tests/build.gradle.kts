plugins {
	kotlin("jvm")
}

dependencies {
	api(gradleTestKit())
	api("io.kotest:kotest-runner-junit5")
	api("io.kotest:kotest-assertions-core")

	api(project(":root-module-plugin"))
}
