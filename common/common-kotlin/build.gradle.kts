plugins {
	id("org.gradle.kotlin.kotlin-dsl")
	`maven-publish`
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			from(components["java"])
		}
	}
}

dependencies {
	compileOnly(kotlin("gradle-plugin"))
	compileOnly(gradleKotlinDsl())

	implementation(projects.common.commonCore)
	implementation(projects.common.commonIdeIdea)

	api(libs.detekt)

	testImplementation(projects.common.commonTests)
}
