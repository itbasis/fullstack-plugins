import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin
import ru.itbasis.gradle.rootmodule.gradleRunConfiguration

plugins {
	`maven-publish`
	id("ru.itbasis.root-module")
}

subprojects {
	repositories {
		gradlePluginPortal()
		jcenter()
	}

	apply<BintrayPlugin>()
	(extra["bintray_user"] as? String)?.let { bintrayUser ->
		(extra["bintray_apikey"] as? String)?.let { bintrayApiKey ->
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
