import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

fun Project.hasBinTrayCredentials() = extra.has("bintray_user1")
