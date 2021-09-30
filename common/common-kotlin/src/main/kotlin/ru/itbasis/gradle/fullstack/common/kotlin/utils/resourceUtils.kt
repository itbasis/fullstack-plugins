package ru.itbasis.gradle.fullstack.common.kotlin.utils

import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import java.io.FileNotFoundException
import java.util.*

internal fun Any.loadPropertiesFromResources(propFileName: String): Properties {
	val props = Properties()
	val inputStream = javaClass.classLoader.getResourceAsStream(propFileName)
		?: throw FileNotFoundException("property file '$propFileName' not found in the classpath")

	inputStream.use { props.load(it) }
	return props
}

fun Any.getAllPropertiesFromResources(propFileName: String) = loadPropertiesFromResources(propFileName = propFileName).map { (k, v) ->
	k as String to v as String
}.toMap()

fun Any.loadPropertyFromResources(propFileName: String, property: String) =
	loadPropertiesFromResources(propFileName = propFileName)[property] as String

fun Project.putExtraKeys(keys: Map<String, String>) = keys.filterKeys {
	!extra.has(it)
}.forEach { (k, v) ->
	extra[k] = v
}
