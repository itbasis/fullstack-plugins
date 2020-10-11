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
		maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
	}
}

gradlePlugin {
	plugins {
		register("ru.itbasis.gradle.root-module-plugin") {
			id = "ru.itbasis.gradle.root-module-plugin"
			implementationClass = "ru.itbasis.gradle.rootmodule.RootModulePlugin"
		}
	}
}

val detektVersion = extra["detekt.version"] as String
val ideaExtVersion = extra["idea-ext.version"] as String

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
	api(kotlin("gradle-plugin"))

	api("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext:gradle-idea-ext:$ideaExtVersion")
	api("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")

	api("com.gradle.publish:plugin-publish-plugin:+")
	api("com.jfrog.bintray.gradle:gradle-bintray-plugin:+")
}
