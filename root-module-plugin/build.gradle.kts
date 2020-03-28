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
			id = "ru.itbasis.root-module"
			implementationClass = "ru.itbasis.gradle.rootmodule.RootModulePlugin"
		}
	}
}

val gradleIdeaExtVersion = extra["gradle-idea-ext.version"] as String
dependencies {
	api(project(":common:common-ide-idea"))
}
