plugins {
	`kotlin-dsl`
}

kotlinDslPluginOptions {
	experimentalWarning.set(false)
}

allprojects {
	repositories {
		gradlePluginPortal()
		jcenter()
	}
}

gradlePlugin {
	plugins {
		register("backend-plugin") {
			id = "ru.itbasis.backend-plugin"
			implementationClass = "ru.itbasis.gradle.BackendPlugin"
		}
		register("ru.itbasis.root-module") {
			id = "ru.itbasis.root-module"
			implementationClass = "ru.itbasis.gradle.rootmodule.RootModulePlugin"
		}
	}
}

val gradleDetektVersion = extra["gradle-detekt.version"] as String
val gradleIdeaExtVersion = extra["gradle-idea-ext.version"] as String

configurations.all {
	resolutionStrategy {
		eachDependency {
			when (requested.group) {
				"org.jetbrains.kotlin" -> useVersion(embeddedKotlinVersion)
			}
		}
	}
}
dependencies {
	api("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext:gradle-idea-ext:$gradleIdeaExtVersion")
	api("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$gradleDetektVersion")

	api("com.gradle.publish:plugin-publish-plugin:+")
	api("com.jfrog.bintray.gradle:gradle-bintray-plugin:+")
}
