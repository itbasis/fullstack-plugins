import com.gradle.publish.PublishPlugin
import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin
import ru.itbasis.gradle.common.ide.idea.gradleRunConfiguration

plugins {
	`maven-publish`
	id("ru.itbasis.root-module")
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

subprojects {
	if (name == "common") {
		return@subprojects
	}

	repositories {
		gradlePluginPortal()
		jcenter()
	}

	apply<PublishPlugin>()
	apply<MavenPublishPlugin>()

	(extra["bintray_user"] as? String)?.let { bintrayUser ->
		(extra["bintray_apikey"] as? String)?.let { bintrayApiKey ->
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
	}
}

gradleRunConfiguration(cfgSubName = "publish (local)", tasks = listOf("check", "publishToMavenLocal"))
gradleRunConfiguration(cfgSubName = "publish", tasks = listOf("check", "bintrayUpload"))
gradleRunConfiguration(
	cfgSubName = "resources",
	tasks = listOf("processResources", "generatePomFileForPluginMavenPublication", "generateMetadataFileForPluginMavenPublication")
) {
	scriptParameters = "--rerun-tasks"
}
