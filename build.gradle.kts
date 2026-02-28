allprojects {
    group = "cafe.zach"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}

tasks.register("build") {
    dependsOn(
        gradle.includedBuild("lib").task(":build"),
        gradle.includedBuild("gtnh-1.7.10").task(":build")
    )
}

tasks.register("clean") {
    dependsOn(
        gradle.includedBuild("lib").task(":clean"),
        gradle.includedBuild("gtnh-1.7.10").task(":clean")
    )
}
