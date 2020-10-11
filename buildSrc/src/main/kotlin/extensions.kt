import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

fun Project.binTrayCredentials() = if (extra.has("BINTRAY_USER")) {
	extra["BINTRAY_USER"] as String to extra["BINTRAY_API_KEY"] as String
}else if (hasProperty("BINTRAY_USER")){
	property("BINTRAY_USER") as String to property("BINTRAY_API_KEY") as String
} else {
	null
}
