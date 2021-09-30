
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

plugins {
	`kotlin-dsl` apply false
	id("com.gradle.plugin-publish").version("0.14.0").apply(false)
	id("io.gitlab.arturbosch.detekt").version("1.18.1")
	id("org.jetbrains.gradle.plugin.idea-ext").version("1.1")
}
// apply<RootModulePlugin>()

// plugins {
// 	id("com.github.ben-manes.versions") version Libs.gradleVersionsPluginVersion
// 	id("com.namics.oss.gradle.license-enforce-plugin") version Libs.gradleLicenseEnforcePluginVersion
// 	id("io.gitlab.arturbosch.detekt") version Libs.gradleDetektPluginVersion
// }

tasks {
	wrapper {
		distributionType = DistributionType.ALL
	}
// 	maybeCreate(PROCESS_RESOURCES_TASK_NAME).dependsOn(
// 		subprojects.filter { 
// 			it.plugins.hasPlugin("org.gradle.maven-publish")
// 		}
// 	)
}

val jdkVersion: String by rootProject.extra
val javaVersion = JavaVersion.toVersion(jdkVersion)

subprojects {
	apply<KotlinPluginWrapper>()

	repositories {
		gradlePluginPortal()
		mavenCentral()
	}

	configure<JavaPluginExtension> {
		sourceCompatibility = javaVersion
		targetCompatibility = javaVersion
	}

	tasks {
		withType<KotlinJvmCompile> {
			kotlinOptions {
				jvmTarget = jdkVersion
				apiVersion = "1.5"
				languageVersion = "1.5"
				freeCompilerArgs = listOf("-Xjsr305=strict")
			}
		}

		withType<Test> {
			useJUnitPlatform()
		}
	}
}
