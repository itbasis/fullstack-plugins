import ru.itbasis.gradle.common.kotlin.CheckstylePlugin
import com.jfrog.bintray.gradle.BintrayExtension

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

binTrayCredentials()?.run {
	configure<BintrayExtension> {
		pkg.apply {
			name = project.name + "-all-plugins"
		}
	}
}

dependencies {
	subprojects.forEach {
		"api"(project(":backend-plugins:$name:${it.name}"))
	}
}

subprojects {
	apply<CheckstylePlugin>()
}
