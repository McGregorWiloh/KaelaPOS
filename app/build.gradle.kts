plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
    //id("com.google.devtools.ksp")
}

android {
    namespace = "com.mcgregor.kaelapos"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mcgregor.kaelapos"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            setProguardFiles(
                    listOf(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                    )
            )

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    // composeOptions {
    //    kotlinCompilerExtensionVersion = "1.4.7"
    // }
    packagingOptions {
        resources {
            pickFirsts.add("META-INF/AL2.0")
            pickFirsts.add("META-INF/LGPL2.1")
        }
    }

    hilt {
        enableAggregatingTask = true
    }
}

composeCompiler {
    enableStrongSkippingMode = true
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    // stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {

    implementation ("androidx.core:core-ktx:1.13.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation ("androidx.activity:activity-compose:1.9.0")
    implementation ("androidx.compose.ui:ui:1.6.8")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation ("androidx.compose.material:material:1.6.8")

    // Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.49")
    implementation ("androidx.core:core-ktx:1.13.1")
    kapt("com.google.dagger:hilt-android-compiler:2.49")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    //material icons - use with caution!
    // implementation "androidx.compose.material:material-icons-extended:$compose_version"
    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")

    //itext PDF library
    implementation ("com.itextpdf:itext7-core:7.1.16")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // JSON Converter
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    //Google Gson (JSON) converter library used to convert Java Objects into their JSON representation.
    //It can also be used to convert a JSON string to an equivalent Java object.
    implementation ("com.google.code.gson:gson:2.11.0")

    //Room
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    // To use Kotlin annotation processing tool (kapt) MUST HAVE!
    kapt("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")

    implementation("androidx.multidex:multidex:2.0.1")

    implementation ("androidx.navigation:navigation-compose:2.7.7")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.8")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.6.8")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.6.8")
}