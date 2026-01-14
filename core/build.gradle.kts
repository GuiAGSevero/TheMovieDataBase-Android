plugins {
    id("java-library")
    kotlin("jvm")
}

dependencies {
    api(platform(Dependencies.OkHttp.bom))
    api(Dependencies.OkHttp.okhttp)
    api(Dependencies.OkHttp.loggingInterceptor)

    api(Dependencies.Retrofit.retrofit)
    api(Dependencies.Retrofit.converterGson)

    api(Dependencies.Gson.gson)

    implementation(Dependencies.Android.Paging.common)

    implementation(Dependencies.JavaxInject.javaxInject)

    api(Dependencies.Coroutines.core)

    testImplementation(project(":testing"))
}
