import org.gradle.api.JavaVersion

object Versions {

    object AndroidX {
        const val ACTIVITY = "1.5.1"
        const val CORE = "1.8.0"
        const val LIFECYCLE = "2.3.1"

        object Test {
            const val ESPRESSO = "3.4.0"
            const val JUNIT = "1.1.3"
        }
    }

    object Build {
        val JAVA_VERSION = JavaVersion.VERSION_1_8
        const val JVM_TARGET = "1.8"

        const val GRADLE_TOOLS = "7.4.0"
        const val KOTLIN_GRADLE = "1.7.20"
        const val KOTLIN_JVM = "1.8.0"
    }

    object Compose {
        const val BOM = "2022.10.00"
        const val BUILD = "1.4.0"
    }

    object Detekt {
        const val DETEKT = "1.19.0"
    }

    object JUnit {
        const val MAIN = "4.13.2"
    }
}
