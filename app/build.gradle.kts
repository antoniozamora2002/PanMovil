plugins {
    id("com.android.application")
    id("org.sonarqube") version "5.0.0.4638"
}

android {
    namespace = "com.example.panmovil_panitasproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.panmovil_panitasproject"
        minSdk = 24
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

    packagingOptions {
        exclude("META-INF/NOTICE.md")
        exclude("META-INF/LICENSE.md")
        // Puedes agregar más exclusiones si es necesario
    }

}
sonarqube {
    properties {
        property("sonar.projectKey", "Pan-Movil-Testing")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", "sqp_ee7eb962172e00058b144103bdb228e31a2134f8")
        property("sonar.login", "admin")
        property("sonar.password", "admin1")
    }
}

dependencies {
    /*implementation ("androidx.activity:activity:1.3.1")*/

    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.android.volley:volley:1.2.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    // Para enviar correos electrónicos en una aplicación Android
    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")
    implementation("com.airbnb.android:lottie:3.4.0") /*ANIMACIONES*/
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("androidx.gridlayout:gridlayout:1.0.0") /* FORMATO JSON */

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.0.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
