import org.jetbrains.gradle.ext.packagePrefix
import org.jetbrains.gradle.ext.settings
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"

    application
}

dependencies {
    implementation("org.apache.arrow:flight-sql:18.2.0")
    implementation("org.apache.arrow:arrow-jdbc:18.2.0")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks {
    withType<JavaCompile> { targetCompatibility = "17" }
    withType<KotlinCompile> { kotlinOptions.jvmTarget = "17" }
    withType<Test> {
        useJUnitPlatform()
        environment("_JAVA_OPTIONS", "--add-opens=java.base/java.nio=ALL-UNNAMED")
    }
    shadowJar {
        setProperty("zip64", true)
        mergeServiceFiles() // https://stackoverflow.com/a/63474092/1815486
    }
}
