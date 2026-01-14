plugins {
    id("java-library")
    kotlin("jvm")
}

dependencies {
    api(project(":core"))

    implementation(Dependencies.Android.Paging.common)
    api(Dependencies.Testing.junit)
    api(Dependencies.Testing.coreTesting)
    api(Dependencies.Testing.coroutinesTest)
    api(Dependencies.Testing.mockitoKotlin)
}
