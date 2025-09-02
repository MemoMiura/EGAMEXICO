plugins {
    id("com.android.application") version "8.1.2" apply false
    id("com.android.library")     version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("org.jetbrains.kotlin.kapt")    version "1.9.24" apply false
    id("androidx.navigation.safeargs") version "2.5.3" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}


subprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                if (requested.name.startsWith("kotlin-stdlib") || requested.name == "kotlin-reflect") {
                    useVersion("1.9.24")
                }
            }
        }
    }
}
