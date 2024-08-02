plugins {
  val kotlinVersion = "1.8.0"
  id("org.springframework.boot") version "3.2.2"
  id("io.spring.dependency-management") version "1.1.6"
  kotlin("jvm") version kotlinVersion
  kotlin("plugin.spring") version kotlinVersion
  kotlin("plugin.jpa") version kotlinVersion
}

group = "mc.plugin"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("com.h2database:h2")
  implementation("mysql:mysql-connector-java:8.0.33")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

val targetJavaVersion = 17
kotlin {
  jvmToolchain(targetJavaVersion)
}
tasks.withType<Test> {
  useJUnitPlatform()
}
