plugins {
    id("com.gtnewhorizons.gtnhconvention")
}

dependencies {
    implementation("cafe.zach:greg-link-lib:0.0.2")
}

val modVersion = System.getenv("GITHUB_REF_NAME")?.removePrefix("v") ?: "dev"

tasks.withType<Jar> {
    archiveBaseName.set("greg-link-gtnh-1.7.10")
    archiveVersion.set(modVersion)
}
