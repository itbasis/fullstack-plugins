import com.gradle.publish.PluginBundleExtension

plugins {
	`maven-publish`

	id("com.gradle.plugin-publish")
}

configure<PluginBundleExtension> {
	tags = listOf("ru.itbasis", "kotlin", "intellij", "idea", "settings")
}

configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("ru.itbasis.gradle.root-module-plugin") {
			id = "ru.itbasis.gradle.root-module-plugin"
			implementationClass = "ru.itbasis.gradle.rootmodule.RootModulePlugin"
		}
	}
}

dependencies {
	api(project(":common:common-core"))
	api(project(":common:common-ide-idea"))
}
