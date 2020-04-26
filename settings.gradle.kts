import org.gradle.api.internal.FeaturePreviews.Feature.GRADLE_METADATA

rootProject.name = "itbasis-fullstack-plugins"

enableFeaturePreview(GRADLE_METADATA.name)

fun includeSubmodules(modulesRootDirName: String) {
	val modulesRootDir = rootDir.resolve(modulesRootDirName.replace(":", File.separator))
	require(modulesRootDir.isDirectory) { "$modulesRootDir is not directory" }

	modulesRootDir.listFiles { dir, name ->
		val moduleDir = dir.resolve(name)
		return@listFiles moduleDir.isDirectory && moduleDir.resolve("build.gradle.kts").isFile
	}?.forEach { moduleDir ->
		include("$modulesRootDirName:${moduleDir.name}")
	}
}

include(
	":common:common-core",
	":common:common-ide-idea",
	":common:common-kotlin",
	":common:common-tests"
)

include(":backend-plugins:backend-base-plugin")

include(
	":backend-plugins:backend-koin",
	":backend-plugins:backend-koin:backend-koin-base-plugin",
	":backend-plugins:backend-koin:backend-koin-library-plugin",
	":backend-plugins:backend-koin:backend-koin-service-plugin"
)

include(
	"backend-plugins:backend-spring-boot",
	"backend-plugins:backend-spring-boot:backend-spring-boot-base-plugin",
	"backend-plugins:backend-spring-boot:backend-spring-boot-library-plugin",
	"backend-plugins:backend-spring-boot:backend-spring-boot-service-plugin"
)

includeSubmodules("flutter-plugins")

include("root-module-plugin")
