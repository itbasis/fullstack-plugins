val ideaExtVersion = extra["idea-ext.version"] as String
dependencies {
	implementation(project(":common:common-core"))

	api("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext:gradle-idea-ext:$ideaExtVersion")
}
