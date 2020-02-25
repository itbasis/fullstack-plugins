plugins {
	`kotlin-dsl`
}

kotlinDslPluginOptions {
	experimentalWarning.set(false)
}

repositories {
	gradlePluginPortal()
	jcenter()
}

gradlePlugin {
	plugins {
		register("ru.itbasis.root-module") {
			id = "ru.itbasis.root-module"
			implementationClass = "ru.itbasis.gradle.rootmodule.RootModulePlugin"
		}
	}
}

val gradleIdeaExtVersion = extra["gradle-idea-ext.version"] as String

dependencies {
	api("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext:gradle-idea-ext:$gradleIdeaExtVersion")

	api("com.gradle.publish:plugin-publish-plugin:+")
	api("com.jfrog.bintray.gradle:gradle-bintray-plugin:+")
}
