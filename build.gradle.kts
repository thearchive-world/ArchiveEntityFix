plugins {
    `java`
    id("com.gradleup.shadow") version "9.2.2"
}

group = "archive"
version = project.property("plugin_version")!!.toString()

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${property("paper_api")}")
    implementation("io.papermc:paperlib:1.0.8")
}

val targetJavaVersion = 21
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

tasks {
    withType(JavaCompile::class).configureEach {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
        options.compilerArgs.add("-Xlint:deprecation")
    }

    processResources {
        val props = mapOf(
            "version" to project.version,
            "api_version" to project.property("api_version")
        )
        filesMatching("paper-plugin.yml") {
            filteringCharset = "UTF-8"
            expand(props)
        }
    }

    jar {
        enabled = false
    }

    shadowJar {
        archiveClassifier = ""
        relocate("io.papermc.lib", "shadow.io.papermc.paperlib")
    }

    build {
        dependsOn(shadowJar)
    }

    register("printProjectName") {
        doLast {
            println(rootProject.name)
        }
    }

    register("release") {
        dependsOn(build)
        doLast {
            if (!version.toString().endsWith("-SNAPSHOT")) {
                shadowJar.get().archiveFile.get().asFile
                    .renameTo(layout.buildDirectory.get().asFile.resolve("libs/${rootProject.name}.jar"))
            }
        }
    }
}
