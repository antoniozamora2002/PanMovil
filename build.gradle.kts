// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.sonarqube") version "5.0.0.4638"
}
sonar {
  properties {
    property("sonar.projectKey", "PanMovil")
    property("sonar.projectName", "PanMovil")
  }
}
