// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "8.4.1" apply false
    id ("com.android.library") version "8.4.1" apply false
    id ("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id ("com.google.dagger.hilt.android") version "2.49" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" // this version matches your Kotlin version
    //id("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false
}