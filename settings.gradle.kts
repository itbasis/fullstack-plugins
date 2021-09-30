@file:Suppress("UnstableApiUsage")

import org.gradle.api.internal.FeaturePreviews.Feature.TYPESAFE_PROJECT_ACCESSORS
import org.gradle.api.internal.FeaturePreviews.Feature.VERSION_CATALOGS

rootProject.name = "itbasis-fullstack-plugins"

enableFeaturePreview(TYPESAFE_PROJECT_ACCESSORS.name)
enableFeaturePreview(VERSION_CATALOGS.name)

pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
	}
	resolutionStrategy {
		eachPlugin {
// 			println("use plugin: ${this.requested}")
// 			when (requested.id.id) {
// 				"org.gradle.kotlin.kotlin-dsl" -> useVersion("2.1.7")
// 			}
		}
	}
}

plugins {
	id("com.gradle.enterprise") version "3.7"
}

gradleEnterprise {
	buildScan {
		termsOfServiceUrl = "https://gradle.com/terms-of-service"
		termsOfServiceAgree = "yes"
	}
}

//
include(
	":common:common-core",
	":common:common-ide-idea",
	":common:common-kotlin",
	":common:common-tests"
)
//
include(":root-module-plugin")
