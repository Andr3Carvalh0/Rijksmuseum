plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "${Configuration.NAMESPACE}.presentation"
    compileSdk = Configuration.COMPILE_SDK

    defaultConfig {
        minSdk = Configuration.MINIMUM_SDK
    }

    compileOptions {
        sourceCompatibility = Versions.Build.JAVA_VERSION
        targetCompatibility = Versions.Build.JAVA_VERSION
    }

    kotlinOptions {
        jvmTarget = Versions.Build.JVM_TARGET
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.BUILD
    }

    packagingOptions {
        resources {
            resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(Modules.DATA)) {
        because(
            "By depending on it, its included in the dependency graph and " +
                "hilt will generate our binds/etc for the data module"
        )
    }
    implementation(project(Modules.DOMAIN))
    implementation(Libraries.Coil)
    implementation(Libraries.AndroidX.Core)
    implementation(Libraries.AndroidX.Lifecycle)
    implementation(Libraries.Compose.Activity)
    implementation(platform(Libraries.Compose.BOM))
    implementation(Libraries.Compose.UI)
    implementation(Libraries.Compose.Graphics)
    implementation(Libraries.Compose.Preview)
    implementation(Libraries.Compose.Material3)
    implementation(Libraries.Compose.Navigation.Core)
    implementation(Libraries.Compose.Lifecycle)
    implementation(Libraries.Hilt.Core)
    implementation(Libraries.Hilt.Navigation)

    kapt(Libraries.Hilt.Compiler)

    debugImplementation(Libraries.Compose.Tooling)
    debugImplementation(Libraries.Compose.Test.Manifest)

    testImplementation(Libraries.JUnit)
    testImplementation(Libraries.Kotlin.Test.Coroutines)
    testImplementation(Libraries.Mockk)
    testImplementation(Libraries.Turbine)
}
