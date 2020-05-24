import com.jfrog.bintray.gradle.BintrayExtension
import ru.itbasis.gradle.common.kotlin.CheckstylePlugin

plugins {
	`kotlin-dsl`
}

subprojects {
	apply<KotlinDslPlugin>()

	configure<KotlinDslPluginOptions> {
		experimentalWarning.set(false)
	}

	if (!name.endsWith("-plugin")) {
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

		if (hasBinTrayCredentials()) {
			configure<BintrayExtension> {
				pkg.apply {
					name = project.name + "-all-plugins"
				}
			}
		}

		dependencies {
			subprojects.forEach {
				api(project(":backend-plugins:$name:${it.name}"))
			}
		}

		subprojects {
			apply<CheckstylePlugin>()
		}
	}
}
