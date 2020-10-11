subprojects {
	if (!name.endsWith("-plugin")) {
		apply(from = rootDir.resolve("gradle/make-all-plugins.gradle.kts"))
	}

	tasks {
		processResources {
			doFirst {
				val f = projectDir.resolve("src/main/resources/project.properties")
				if (!f.isFile) {
					f.parentFile.mkdirs()
					f.writeText("project.version=\${project.version}")
				}
			}
			filesMatching("project.properties") {
				expand(project.properties)
			}
		}
	}
}
