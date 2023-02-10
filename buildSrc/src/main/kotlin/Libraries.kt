object Libraries {

    object AndroidX {
        const val Core = "androidx.core:core-ktx:${Versions.AndroidX.CORE}"
        const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.LIFECYCLE}"
    }

    const val Coil = "io.coil-kt:coil-compose:${Versions.COIL}"

    object Compose {
        const val Activity = "androidx.activity:activity-compose:${Versions.AndroidX.ACTIVITY}"
        const val BOM = "androidx.compose:compose-bom:${Versions.Compose.BOM}"
        const val UI = "androidx.compose.ui:ui"
        const val Material3 = "androidx.compose.material3:material3"
        const val Graphics = "androidx.compose.ui:ui-graphics"
        const val Preview = "androidx.compose.ui:ui-tooling-preview"
        const val Tooling = "androidx.compose.ui:ui-tooling"
        const val Lifecycle = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.LIFECYCLE}"

        object Navigation {
            const val Core = "androidx.navigation:navigation-compose:${Versions.Compose.NAVIGATION}"
        }

        object Test {
            const val Manifest = "androidx.compose.ui:ui-test-manifest"
        }
    }

    object Detekt {
        const val Formatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.Detekt.DETEKT}"
    }

    object Hilt {
        const val Core = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val Compiler = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val Navigation = "androidx.hilt:hilt-navigation-compose:${Versions.Compose.HILT}"
    }

    const val JUnit = "junit:junit:${Versions.JUnit.MAIN}"

    object Kotlin {
        const val Coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.COROUTINES}"

        object Test {
            const val Coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Kotlin.COROUTINES}"
        }
    }

    object Moshi {
        const val Core = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
        const val Adapters = "com.squareup.moshi:moshi-adapters:${Versions.MOSHI}"
    }

    object OKHttp {
        const val Logging = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
        const val MockServer = "com.squareup.okhttp3:mockwebserver:${Versions.OKHTTP}"
    }

    object Retrofit {
        const val Core = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val Moshi = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"
    }

    const val Mockk = "io.mockk:mockk:${Versions.MOCKK}"

    const val Turbine = "app.cash.turbine:turbine:${Versions.TURBINE}"
}
