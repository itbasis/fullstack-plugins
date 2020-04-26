configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("ru.itbasis.gradle.flutter-app-plugin") {
			id = "ru.itbasis.gradle.flutter-app-plugin"
			implementationClass = "ru.itbasis.gradle.flutter.app.FlutterAppPlugin"
		}
	}
}

dependencies {
	api(project(":flutter-plugins:flutter-core-plugin"))
}
