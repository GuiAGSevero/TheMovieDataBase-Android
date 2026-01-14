plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("io.gitlab.arturbosch.detekt")
}

apply {
    from("../config/detekt/detekt.gradle")
}

android {
    namespace = Dependencies.Project.demoApplicationNamespace
    compileSdk = Dependencies.Android.compileSdk

    defaultConfig {
        applicationId = Dependencies.Project.applicationId
        minSdk = Dependencies.Android.minSdk
        targetSdk = Dependencies.Android.targetSdk
        versionCode = Dependencies.Project.versionCode
        versionName = Dependencies.Project.versionName

        testInstrumentationRunner = "com.severo.marvel.CustomTestRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"

        buildFeatures.buildConfig = true

        buildConfigField("String", "PUBLIC_KEY", "\"50f821f2abd2884534d41ad6dbc9b46d\"")
        buildConfigField("String", "PRIVATE_KEY", "\"072dc1ec8fb0dbdb051a94649d41c8f30f0d8121\"")
        buildConfigField("String", "BASE_URL", "\"https://gateway.marvel.com/v1/public/\"")
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
        create("staging") {
            initWith(getByName("debug"))
            isMinifyEnabled = true
            applicationIdSuffix = ".staging"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-rules-staging.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))

    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.constraintLayout)

    implementation(Dependencies.Android.material)
    implementation(Dependencies.Android.legacySupport)

    implementation(Dependencies.Android.Navigation.fragment)
    implementation(Dependencies.Android.Navigation.ui)

    implementation(Dependencies.Android.Lifecycle.viewModel)
    implementation(Dependencies.Android.Lifecycle.liveData)
    implementation(Dependencies.Android.Lifecycle.runtime)
    implementation(Dependencies.Android.Lifecycle.runtimeLegacy)

    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Coroutines.android)

    implementation(Dependencies.Koin.android)

    implementation(Dependencies.Android.Room.ktx)
    implementation(Dependencies.Android.Room.runtime)
    implementation(Dependencies.Android.Room.paging)
    kapt(Dependencies.Android.Room.compiler)

    implementation(Dependencies.Android.Paging.runtime)

    implementation(Dependencies.Glide.implementation)
    kapt(Dependencies.Glide.compiler)

    implementation(Dependencies.Android.shimmer)
    implementation(Dependencies.Android.dataStore)

    testImplementation(project(":testing"))
    testImplementation(Dependencies.Android.Room.testing)

    androidTestImplementation(Dependencies.Android.Test.junitExt)
    androidTestImplementation(Dependencies.Android.Test.runner)
    androidTestUtil(Dependencies.Android.Test.orchestrator)

    androidTestImplementation(Dependencies.Android.Espresso.core)
    androidTestImplementation(Dependencies.Android.Espresso.contrib)

    debugImplementation(Dependencies.Android.Fragment.testing)

    androidTestImplementation(Dependencies.OkHttp.mockWebServer)
    androidTestImplementation(Dependencies.Coroutines.test)
    androidTestImplementation(Dependencies.Android.Navigation.testing)
}

