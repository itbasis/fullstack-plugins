import com.gradle.publish.PluginBundleExtension

plugins {
	`maven-publish`
}

subprojects {
	configure<PluginBundleExtension> {
		tags = listOf("ru.itbasis", "kotlin", "koin", "ktor")
	}
}
