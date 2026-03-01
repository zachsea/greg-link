plugins {
    id("java-library")
    id("maven-publish")
    id("com.diffplug.spotless") version "6.25.0"
}

repositories {
    mavenCentral()
    mavenLocal()
}

group = "cafe.zach"
version = "0.0.1"

apply(from = "${rootProject.projectDir.parentFile}/gradle/shared/spotless.gradle")

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

configurations.named("runtimeElements") {
    attributes {
        attribute(
            Attribute.of("com.gtnewhorizons.retrofuturagradle.obfuscation", String::class.java),
            "mcp"
        )
        attribute(
            Attribute.of("rfgDeobfuscatorTransformed", Boolean::class.javaObjectType),
            true
        )
    }
}

configurations.named("apiElements") {
    attributes {
        attribute(
            Attribute.of("com.gtnewhorizons.retrofuturagradle.obfuscation", String::class.java),
            "mcp"
        )
        attribute(
            Attribute.of("rfgDeobfuscatorTransformed", Boolean::class.javaObjectType),
            true
        )
    }
}
