plugins {
	`kotlin-dsl`
}

configure<KotlinDslPluginOptions> {
	experimentalWarning.set(false)
}

val gradleDetektVersion = extra["gradle-detekt.version"] as String

dependencies {
	api("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$gradleDetektVersion")
}
