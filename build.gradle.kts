// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.sonarqube") version "5.0.0.4638"
}

//sonarqube {
  //  properties {
    //    property("sonar.projectKey", "Pan-Movil-Testing")
      //  property("sonar.host.url", "http://localhost:9000")
        //property("sonar.token", "sqp_ee7eb962172e00058b144103bdb228e31a2134f8")
        //property("sonar.login", "admin")
        //property("sonar.password", "admin1")
    //}
    //}
sonar {
  properties {
    property "sonar.projectKey", "PanMovil"
    property "sonar.projectName", "PanMovil"
  }
}
