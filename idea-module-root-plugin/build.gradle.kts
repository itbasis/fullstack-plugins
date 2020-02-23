plugins {
	`kotlin-dsl`
	`maven-publish`
}

gradlePlugin {
	plugins {
		register("plugin") {
			id = "ru.itbasis.idea-module-root"
			implementationClass = "ru.itbasis.gradle.ideamoduleroot.IdeaModuleRootPlugin"
		}
	}
}

dependencies {
	api("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext:gradle-idea-ext:0.7")
}
