import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "database"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)

            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation(libs.koin.core)
        }
    }

    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }
}

android {
    namespace = "core.database"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspCommonMainMetadata", libs.room.compiler)
}

tasks {
    withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
        if (name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}