@file:Suppress("UnstableApiUsage")

rootProject.name = "itbasis-fullstack-plugins"

plugins {
	`gradle-enterprise`
}

gradleEnterprise {
	buildScan {
		termsOfServiceUrl = "https://gradle.com/terms-of-service"
		termsOfServiceAgree = "yes"
	}
}

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
	":flutter-plugins:flutter-core-plugin",
	":flutter-plugins:flutter-library-plugin",
	":flutter-plugins:flutter-app-plugin"
)

include("root-module-plugin")
