dependencies {
	api(gradleTestKit())
	api(gradleKotlinDsl())

	implementation(projects.rootModulePlugin)

	api(libs.bundles.kotestBase)
}
