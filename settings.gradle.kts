import org.gradle.api.internal.FeaturePreviews.Feature.GRADLE_METADATA

rootProject.name = "itbasis-fullstack-plugins"

enableFeaturePreview(GRADLE_METADATA.name)

include(":common:common-ide-idea")
include(":common:common-backend")

include(":spring-boot-backend:spring-boot-backend-tests-plugin")

include(":spring-boot-backend:spring-boot-backend-versions-plugin")
include(":spring-boot-backend:spring-boot-backend-library-plugin")

include(":spring-boot-backend:spring-boot-backend-base-plugin")

include(":spring-boot-backend:spring-boot-backend-library-db-plugin")

include(":spring-boot-backend:spring-boot-backend-service-plugin")

include("root-module-plugin")

//include(":spring-boot-backend:spring-boot-backend-all-plugins")
