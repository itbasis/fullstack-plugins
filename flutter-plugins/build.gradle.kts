import com.gradle.publish.PluginBundleExtension
import com.jfrog.bintray.gradle.BintrayExtension
import ru.itbasis.gradle.common.kotlin.CheckstylePlugin

plugins {
	`kotlin-dsl`
	`maven-publish`
}

configure<PublishingExtension> {
	publications {
		maybeCreate("pluginMaven", MavenPublication::class).apply {
			pom {
				packaging = "pom"
				artifactId = project.name + "-all-plugins"
			}
		}
	}
}

configure<BintrayExtension> {
	pkg.apply {
		name = project.name + "-all-plugins"
	}
}

dependencies {
	subprojects.forEach {
		api(project(":$name:${it.name}"))
	}
}

subprojects {
	apply<KotlinDslPlugin>()
	apply<CheckstylePlugin>()

	configure<KotlinDslPluginOptions> {
		experimentalWarning.set(false)
	}

	configure<PluginBundleExtension> {
		tags = listOf("ru.itbasis", "flutter")
	}

}
