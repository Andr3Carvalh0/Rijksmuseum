plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "${Configuration.NAMESPACE}.data"
    compileSdk = Configuration.COMPILE_SDK

    compileOptions {
        sourceCompatibility = Versions.Build.JAVA_VERSION
        targetCompatibility = Versions.Build.JAVA_VERSION
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

    kapt(Libraries.Hilt.Compiler)

    testImplementation(Libraries.JUnit)
}
