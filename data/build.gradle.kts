plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "${Configuration.NAMESPACE}.data"
    compileSdk = Configuration.TARGET_SDK

    compileOptions {
        sourceCompatibility = Versions.Build.JAVA_VERSION
        targetCompatibility = Versions.Build.JAVA_VERSION
    }

    kotlinOptions {
        jvmTarget = Versions.Build.JVM_TARGET
    }
}

dependencies {
    implementation(project(Modules.DOMAIN))
    testImplementation(Libraries.JUnit)
}
