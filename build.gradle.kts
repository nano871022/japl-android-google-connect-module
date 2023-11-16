plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "co.com.japl.connect.gdrive"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "GOOGLE_APPLICATION_CREDENTIALS", project.findProperty("GOOGLE_APPLICATION_CREDENTIALS")?.toString() ?: "")
        buildConfigField("String", "FOLDER_ID", project.findProperty("FOLDER_ID")?.toString() ?: "")
        buildConfigField("String", "URL_INITIAL", project.findProperty("URL_INITIAL")?.toString() ?: "")
        buildConfigField("String", "APPLICATION_NAME", project.findProperty("APPLICATION_NAME")?.toString() ?: "")
        buildConfigField("String", "MIME_TYPE_IMAGE", project.findProperty("MIME_TYPE_IMAGE")?.toString() ?: "")
    }

    buildFeatures{
        viewBinding
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

    packagingOptions {
        resources.excludes.add("META-INF/DEPENDENCIES")
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("com.google.apis:google-api-services-drive:v3-rev197-1.25.0")
    implementation("com.google.api-client:google-api-client:1.15.0")
    implementation("com.google.api-client:google-api-client-android:1.23.0")
    implementation("com.google.http-client:google-http-client-gson:1.42.3")
    implementation("com.google.http-client:google-http-client-jackson2:1.43.3")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.11.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.android.gms:play-services-drive:17.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}