plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.edith"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.edith"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources.excludes.addAll(
                listOf(
                        "META-INF/LICENSE.md",
                        "META-INF/LICENSE-notice.md",
        )
        )
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    // Google Authentication Stuff
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation ("com.google.android.gms:play-services-auth:21.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.firebase:firebase-firestore:24.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("org.testng:testng:7.4.0")
    testImplementation("org.testng:testng:7.4.0")
    testImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5@aar")

    //For google calendar api
    implementation("com.google.api-client:google-api-client-android:1.31.5")
    implementation("com.google.api-client:google-api-client-gson:1.31.5")
    implementation ("com.google.apis:google-api-services-calendar:v3-rev20220715-2.0.0")

    implementation ("com.google.http-client:google-http-client-android:1.39.2")
    implementation ("com.google.http-client:google-http-client-jackson2:1.39.2")



    // For CircleImageView Library
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // For Navigation Drawer
    implementation("com.google.android.material:material:1.11.0")

    // For Circular Create Event Button
    implementation("com.google.android.material:material:1.11.0")

    // For RecyclerViewSwipe Decorator
    implementation("it.xabaras.android:recyclerview-swipedecorator:1.4")

    // For Expandable Material Card View
    //implementation("com.thoughtbot:expandablerecyclerview:1.4")
}