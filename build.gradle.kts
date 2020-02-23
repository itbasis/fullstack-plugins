plugins {
	`maven-publish`
}

subprojects {
	repositories {
		gradlePluginPortal()
		jcenter()
	}
}
