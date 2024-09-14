plugins {
    val kotlinVersion = "1.8.0"
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "mc.plugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.ktor:ktor-serialization-jackson:2.3.0")
    implementation("com.h2database:h2")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.json:json:20210307")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.ktor:ktor-client-cio:2.3.0")
}

val targetJavaVersion = 17
kotlin {
    jvmToolchain(targetJavaVersion)
}
tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    archiveVersion.set("paperspring")
}

tasks.register<Jar>("combineJars") {
    dependsOn("bootJar", "shadowJar")
    from(
        zipTree(
            tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar")
                .get().archiveFile.get().asFile
        )
    ) {
        exclude("gg/**", "gg/flyte/template/PluginTemplate.kt", "gg/flyte/template/**")
    }
    from(
        zipTree(
            tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar")
                .get().archiveFile.get().asFile
        )
    ) {
        // Optional: exclude duplicate files if necessary
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA", "hello/**")
    }
    archiveClassifier.set("combined")
}

tasks.register("buildCombined") {
    dependsOn("combineJars")
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}
