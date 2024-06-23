// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.sonarqube") version "5.0.0.4638"
}
sonar {
  properties {
    property("sonar.projectKey", "sadasd")
    property("sonar.host.url", "http://ec2-52-87-8-79.compute-1.amazonaws.com:9000")
    property("sonar.token", "squ_83076f2e1ac351f95cbbe16d427d3bd387cfd8d0")
  }
}
