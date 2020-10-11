import com.gradle.publish.PublishPlugin
import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import ru.itbasis.gradle.common.ide.idea.gradleRunConfiguration

plugins {
	`kotlin-dsl`
	`maven-publish`
	id("ru.itbasis.gradle.root-module-plugin")
}

allprojects {
	configurations.all {
		resolutionStrategy {
			eachDependency {
				when (requested.group) {
					"org.jetbrains.kotlin" -> useVersion(embeddedKotlinVersion)
				}
			}
		}
	}
}
val kotestVersion = extra["kotest.version"] as String

subprojects {
	apply<KotlinDslPlugin>()
	configure<KotlinDslPluginOptions> {
		experimentalWarning.set(false)
	}

	repositories {
		gradlePluginPortal()
	}

	if (name == "common" || name == "backend-plugins") {
		return@subprojects
	}

	apply<PublishPlugin>()
	apply<MavenPublishPlugin>()
	if (hasBinTrayCredentials()) {
		val bintrayUser = extra["bintray_user"] as String
		val bintrayApiKey = extra["bintray_apikey"] as String

		apply<BintrayPlugin>()
		configure<BintrayExtension> {
			user = bintrayUser
			key = bintrayApiKey
			override = true
			publish = true
			setPublications("pluginMaven")
			pkg.apply {
				repo = group as String
				name = this@subprojects.name
			}
		}
	}

	tasks {
		withType(Test::class) {
			useJUnitPlatform()
			testLogging {
				showExceptions = true
				showStandardStreams = true
				events = setOf(FAILED, PASSED)
				exceptionFormat = FULL
			}
		}
	}

	dependencies {
		"testImplementation"(project(":root-module-plugin"))
		if (name != "common-tests") {
			"testImplementation"(project(":common:common-tests"))
		}
	}

	configurations.all {
		resolutionStrategy {

			eachDependency {
				when (requested.group) {
					"io.kotest" -> useVersion(kotestVersion)
				}
			}
		}
	}
}

gradleRunConfiguration(cfgSubName = "publish (local)", tasks = listOf("check", "publishToMavenLocal")) {
	scriptParameters = "-x test --rerun-tasks"
}
gradleRunConfiguration(cfgSubName = "publish", tasks = listOf("check", "bintrayUpload"))
gradleRunConfiguration(cfgSubName = "publish (without tests)", tasks = listOf("check", "bintrayUpload")) {
	scriptParameters = "-x test"
}
gradleRunConfiguration(
	cfgSubName = "resources",
	tasks = listOf("processResources", "generatePomFileForPluginMavenPublication", "generateMetadataFileForPluginMavenPublication")
) {
	scriptParameters = "--rerun-tasks"
}
