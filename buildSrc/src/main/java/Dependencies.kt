object Dependencies {

    object Project {
        const val applicationId = "com.severo.marvel"
        const val demoApplicationNamespace = "com.severo.marvel"

        const val versionCode = 1
        const val versionName = "1.0.0"
    }

    object Android {
        const val compileSdk = 35
        const val minSdk = 21
        const val targetSdk = 35

        const val coreKtx = "androidx.core:core-ktx:1.13.1"
        const val appCompat = "androidx.appcompat:appcompat:1.7.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val material = "com.google.android.material:material:1.12.0"
        const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"
        const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"
        const val dataStore = "androidx.datastore:datastore-preferences:1.1.1"

        object Navigation {
            private const val version = "2.7.7"
            private const val group = "androidx.navigation"
            const val classpath = "$group:navigation-safe-args-gradle-plugin:$version"
            const val fragment = "$group:navigation-fragment-ktx:$version"
            const val ui = "$group:navigation-ui-ktx:$version"
            const val testing = "$group:navigation-testing:$version"
        }

        object Lifecycle {
            private const val version = "2.8.3"
            private const val group = "androidx.lifecycle"
            const val viewModel = "$group:lifecycle-viewmodel-ktx:$version"
            const val liveData = "$group:lifecycle-livedata-ktx:$version"
            const val runtime = "$group:lifecycle-runtime-ktx:$version"

            const val runtimeLegacy = "$group:lifecycle-runtime-ktx:2.6.1"
        }

        object Room {
            private const val version = "2.6.1"
            private const val group = "androidx.room"
            const val ktx = "$group:room-ktx:$version"
            const val runtime = "$group:room-runtime:$version"
            const val paging = "$group:room-paging:$version"
            const val testing = "$group:room-testing:$version"
            const val compiler = "$group:room-compiler:$version"
        }

        object Paging {
            private const val versionCommon = "3.1.1"
            private const val versionRuntime = "3.3.0"
            const val common = "androidx.paging:paging-common:$versionCommon"
            const val runtime = "androidx.paging:paging-runtime-ktx:$versionRuntime"
        }

        object Fragment {
            private const val version = "1.5.3"
            const val testing = "androidx.fragment:fragment-testing:$version"
        }

        object Espresso {
            private const val version = "3.4.0"
            const val core = "androidx.test.espresso:espresso-core:$version"
            const val contrib = "androidx.test.espresso:espresso-contrib:$version"
        }

        object Test {
            const val junitExt = "androidx.test.ext:junit:1.2.1"
            const val runner = "androidx.test:runner:1.6.1"
            const val orchestrator = "androidx.test:orchestrator:1.5.0"
        }
    }

    object Glide {
        const val version = "4.12.0"
        const val implementation = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Coroutines {
        private const val version = "1.6.4"
        private const val base = "org.jetbrains.kotlinx:kotlinx-coroutines"
        const val core = "$base-core:$version"
        const val android = "$base-android:$version"
        const val test = "$base-test:$version"
    }

    object OkHttp {
        private const val version = "4.9.0"
        private const val group = "com.squareup.okhttp3"
        const val bom = "$group:okhttp-bom:$version"
        const val okhttp = "$group:okhttp:$version"
        const val loggingInterceptor = "$group:logging-interceptor:$version"
        const val mockWebServer = "$group:mockwebserver:4.9.3"
    }

    object Retrofit {
        private const val version = "2.9.0"
        private const val group = "com.squareup.retrofit2"
        const val retrofit = "$group:retrofit:$version"
        const val converterGson = "$group:converter-gson:$version"
    }

    object Gson {
        private const val version = "2.8.6"
        const val gson = "com.google.code.gson:gson:$version"
    }

    object JavaxInject {
        private const val version = "1"
        const val javaxInject = "javax.inject:javax.inject:$version"
    }

    object Koin {
        private const val version = "3.2.2"
        const val android = "io.insert-koin:koin-android:$version"
    }

    object Testing {
        const val junit = "junit:junit:4.13.2"
        const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
        const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    }
}
