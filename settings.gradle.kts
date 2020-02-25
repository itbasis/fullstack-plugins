import org.gradle.api.internal.FeaturePreviews.Feature.GRADLE_METADATA

rootProject.name = "itbasis-fullstack-plugins"

enableFeaturePreview(GRADLE_METADATA.name)

include("spring-boot-backend-tests-plugin")

include("spring-boot-backend-versions-plugin")
include("spring-boot-backend-library-plugin")

include("spring-boot-backend-base-plugin")

include("spring-boot-backend-library-db-plugin")

include("spring-boot-backend-service-plugin")

include("root-module-plugin")

include("spring-boot-backend-all-plugins")
