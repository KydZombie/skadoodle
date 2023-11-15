plugins {
    id("babric-loom").version("1.4.2")
    id("maven-publish")
    kotlin("jvm").version("1.9.20")
}

base { archivesName.set(project.extra["archives_base_name"] as String) }
version = project.extra["mod_version"] as String
group = project.extra["maven_group"] as String

loom {
    gluedMinecraftJar()
    customMinecraftManifest.set("https://babric.github.io/manifest-polyfill/${project.extra["minecraft_version"]}.json")
    intermediaryUrl.set("https://maven.glass-launcher.net/babric/babric/intermediary/%1\$s/intermediary-%1\$s-v2.jar")
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.

    // Used for the fabric toolchain in this project.
    maven("https://maven.glass-launcher.net/babric") { name = "Babric" }
    // Used for mappings.
    maven("https://maven.glass-launcher.net/releases") { name = "Glass Releases" }
    // Used for StationAPI and HowManyItems.
    maven("https://maven.glass-launcher.net/snapshots") { name = "Glass Snapshots" }
    // Used for a StationAPI dependency.
    maven("https://maven.minecraftforge.net/") { name = "Froge" }
    // Used for projects that do not have a maven repository, but do have a GitHub repository with working build scripts.
    maven("https://jitpack.io") { name = "Jitpack" }
    // Used for another StationAPI dependency
    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven") { name = "Modrinth" }
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
    mavenCentral()
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang", "minecraft", project.extra["minecraft_version"] as String)
    mappings("net.glasslauncher", "biny", project.extra["yarn_mappings"] as String, "", "v2")
    modImplementation("babric", "fabric-loader", project.extra["loader_version"] as String)

    implementation("org.slf4j", "slf4j-api", "1.8.0-beta4")
    implementation("org.apache.logging.log4j", "log4j-slf4j18-impl", "2.17.2")

    modImplementation("net.modificationstation", "StationAPI", project.extra["stapi_version"] as String)

    // Optional, but convenient mods for mod creators and users alike.
    modImplementation("com.github.calmilamsy", "ModMenu", project.extra["modmenu_version"] as String) { isTransitive = false }
//    modImplementation("net.glasslauncher.mods", "GlassConfigAPI", project.extra["gcapi_version"] as String) { isTransitive = false }
//    modImplementation("net.glasslauncher", "HowManyItems-Fabric-Unofficial", project.extra["howmanyitems_version"] as String) { isTransitive = false }
    modImplementation("net.fabricmc", "fabric-language-kotlin", project.extra["fabric_language_kotlin"] as String) { exclude(module = "fabric-loader") }
}

tasks {
    val javaVersion = JavaVersion.toVersion((project.extra["java_version"] as String).toInt())
    withType<JavaCompile> {
        // Minecraft 1.18 (1.18-pre2) upwards uses Java 17.
        // Loom also requires J17.
        options.release.set(javaVersion.toString().toInt())
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions { jvmTarget = javaVersion.toString() } }
    jar { from("LICENSE") { rename { "${it}_${base.archivesName.get()}" } } }
    processResources {
        filesMatching("fabric.mod.json") { expand(mutableMapOf("version" to project.extra["mod_version"] as String)) }
        filesMatching("*.mixins.json") { expand(mutableMapOf("java" to project.extra["java_version"] as String)) }
    }
    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(javaVersion.toString())) }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}