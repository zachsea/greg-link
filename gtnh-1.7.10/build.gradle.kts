plugins {
    id("com.gtnewhorizons.gtnhconvention")
}

val modVersion = System.getenv("GITHUB_REF_NAME")?.removePrefix("v") ?: "dev"

tasks.withType<Jar> {
    archiveBaseName.set("greg-link-gtnh-1.7.10")
    archiveVersion.set(modVersion)
}
