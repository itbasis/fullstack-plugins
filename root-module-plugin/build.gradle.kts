plugins {
	id("org.gradle.kotlin.kotlin-dsl")

	`maven-publish`
	id("com.gradle.plugin-publish")
}

pluginBundle {
	tags = listOf("ru.itbasis", "kotlin", "intellij", "idea", "settings")
}

dependencies {
	api(projects.common.commonCore)

	implementation(projects.common.commonIdeIdea)
}
