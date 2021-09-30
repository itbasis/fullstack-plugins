plugins {
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
	implementation(gradleApi())
	implementation(gradleKotlinDsl())

	implementation(projects.common.commonCore)

	api("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext", "gradle-idea-ext", "1.1")
}
