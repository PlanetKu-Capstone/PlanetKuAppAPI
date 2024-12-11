// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply true
    alias(libs.plugins.kotlin.android) apply true
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin) apply true
    alias(libs.plugins.google.gms.google.services) apply true
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
}