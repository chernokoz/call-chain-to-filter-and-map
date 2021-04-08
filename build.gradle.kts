import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
}

group = "com.chernokoz.internship"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    kotlin("test-junit5")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("com.chernokoz.internship.MainKt")
}

tasks.test {
    useJUnitPlatform()
}

