package ru.itbasis.gradle.common.core

import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

fun Project.getExtraValueOrDefault(key: String, defaultValue: String) = if (extra.has(key)) extra[key].toString() else defaultValue
