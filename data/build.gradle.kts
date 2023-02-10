plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

fun environmentValue(key: String): String =
    "${System.getenv().getOrDefault(key, project.findProperty(key))}"

android {
    namespace = "${Configuration.NAMESPACE}.data"
    compileSdk = Configuration.COMPILE_SDK

    compileOptions {
        sourceCompatibility = Versions.Build.JAVA_VERSION
        targetCompatibility = Versions.Build.JAVA_VERSION
    }

    defaultConfig {
        minSdk = Configuration.MINIMUM_SDK

        buildConfigField(
            type = "String",
            name = "RIJKSMUSEUM_API_KEY",
            value = "\"${environmentValue("RIJKSMUSEUM_API_KEY")}\""
        )

        buildConfigField(
            type = "String",
            name = "RIJKSMUSEUM_URL",
            value = "\"https://www.rijksmuseum.nl\""
        )
    }

    kotlinOptions {
        jvmTarget = Versions.Build.JVM_TARGET
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(Modules.DOMAIN))
    implementation(Libraries.Hilt.Core)
    implementation(Libraries.Retrofit.Core)
    implementation(Libraries.Retrofit.Moshi)
    implementation(Libraries.Moshi.Core)
    implementation(Libraries.Moshi.Adapters)
    implementation(Libraries.OKHttp.Logging)

    kapt(Libraries.Hilt.Compiler)

    testImplementation(Libraries.JUnit)
    testImplementation(Libraries.Kotlin.Test.Coroutines)
    testImplementation(Libraries.Mockk)
    testImplementation(Libraries.OKHttp.MockServer)
    testImplementation(Libraries.Turbine)
}
