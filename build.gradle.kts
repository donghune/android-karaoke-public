buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
        classpath("com.google.gms:google-services:4.3.15")
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {

    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
}

subprojects {
    repositories {
        mavenCentral()
        google()
    }
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
