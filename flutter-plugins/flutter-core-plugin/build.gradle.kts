configure<GradlePluginDevelopmentExtension> {
	plugins {
		register("ru.itbasis.gradle.flutter-core-plugin") {
			id = "ru.itbasis.gradle.flutter-core-plugin"
			implementationClass = "ru.itbasis.gradle.flutter.core.FlutterCorePlugin"
		}
	}
}

dependencies {
}
