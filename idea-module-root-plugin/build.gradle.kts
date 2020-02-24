plugins {
	`kotlin-dsl`
	`maven-publish`

	id("com.gradle.plugin-publish")
}

pluginBundle {
	tags = listOf("ru.itbasis", "kotlin", "intellij", "idea", "settings")
}

gradlePlugin {
	plugins {
		register("plugin") {
			id = "ru.itbasis.idea-module-root"
			implementationClass = "ru.itbasis.gradle.ideamoduleroot.IdeaModuleRootPlugin"
		}
	}
}

val gradleIdeaExtVersion = extra["gradle-idea-ext.version"] as String
dependencies {
	api("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext:gradle-idea-ext:$gradleIdeaExtVersion")
}
