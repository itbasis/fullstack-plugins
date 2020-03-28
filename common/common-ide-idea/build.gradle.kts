plugins {
	`kotlin-dsl`
}

configure<KotlinDslPluginOptions> {
	experimentalWarning.set(false)
}

val gradleIdeaExtVersion = extra["gradle-idea-ext.version"] as String
dependencies {
	api("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext:gradle-idea-ext:$gradleIdeaExtVersion")
}
