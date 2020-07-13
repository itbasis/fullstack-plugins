package ru.itbasis.gradle.backend.utils

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.gradle.kotlin.dsl.extra
import org.gradle.testfixtures.ProjectBuilder
import ru.itbasis.gradle.common.tests.initTestProject

class ResourceUtilsTest : FunSpec(
	{
		test("put new extra keys") {
			val project = initTestProject()

			project.extra.has("kotlin.version") shouldBe false

			project.putExtraKeys(keys = mapOf("kotlin.version" to "1.3.61"))
			project.extra.get("kotlin.version") shouldBe "1.3.61"

			project.putExtraKeys(keys = mapOf("kotlin.version" to "1.3.71"))
			project.extra.get("kotlin.version") shouldBe "1.3.61"
		}

		test("put exist extra key"){

			val project = initTestProject{
//				withProjectDir()
			}
		}
	}
)
