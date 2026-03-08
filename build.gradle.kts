allprojects {
    group = "cafe.zach"
    version = "0.0.3"

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

tasks.register("spotlessCheck") {
    dependsOn(
        gradle.includedBuild("lib").task(":spotlessCheck"),
        gradle.includedBuild("gtnh-1.7.10").task(":spotlessCheck")
    )
}

tasks.register("spotlessApply") {
    dependsOn(
        gradle.includedBuild("lib").task(":spotlessApply"),
        gradle.includedBuild("gtnh-1.7.10").task(":spotlessApply")
    )
}
