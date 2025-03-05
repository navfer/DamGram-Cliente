import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

}


kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            //koin
            implementation("io.insert-koin:koin-android:4.0.1")
            implementation("io.insert-koin:koin-androidx-compose:4.0.1")
            //ktor
            implementation(libs.ktor.client.android)
            implementation(libs.io.insert.koin.koin.android) // ✅ Koin para Android
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha11")
            implementation("org.jetbrains.compose.material:material-icons-extended:1.5.0")

            //iconos
            implementation("org.jetbrains.compose.material:material-icons-extended:1.5.0")
            //selecciona fichero
            //https://github.com/vinceglb/FileKit
            implementation("io.github.vinceglb:filekit-core:0.8.8")

            //adaptativo
            implementation("org.jetbrains.compose.material3.adaptive:adaptive:1.0.0-alpha03")
            implementation("org.jetbrains.compose.material3.adaptive:adaptive-layout:1.0.0-alpha03")
            implementation("org.jetbrains.compose.material3.adaptive:adaptive-navigation:1.0.0-alpha03")
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation("org.jetbrains.compose.material3:material3-window-size-class:1.7.3")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")

            // Enables FileKit with Composable utilities
            implementation("io.github.vinceglb:filekit-compose:0.8.8")
            //imagenes
            implementation("io.coil-kt.coil3:coil-compose:3.1.0")
            implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            runtimeOnly(libs.insert.koin.koin.compose.viewmodel)
            implementation(libs.insert.koin.koin.compose.viewmodel)

            implementation("io.ktor:ktor-serialization-gson:3.1.0")
            implementation("com.google.code.gson:gson:2.12.1")
            implementation("io.ktor:ktor-client-content-negotiation:3.1.0")
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
            implementation("org.jetbrains.skiko:skiko:0.8.15")
            implementation("org.slf4j:slf4j-api:2.0.9") // Example SLF4J version
            implementation("ch.qos.logback:logback-classic:1.4.11") // Logback implementation
        }
    }
}

android {
    namespace = "org.navfer.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.navfer.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.firebase.firestore.ktx)
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.navfer.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.navfer.project"
            packageVersion = "1.0.0"
        }
    }
}
