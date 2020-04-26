configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("ru.itbasis.gradle.flutter-library-plugin") {
			id = "ru.itbasis.gradle.flutter-library-plugin"
			implementationClass = "ru.itbasis.gradle.flutter.library.FlutterLibraryPlugin"
		}
	}
}

dependencies {
	api(project(":flutter-plugins:flutter-core-plugin"))
}
