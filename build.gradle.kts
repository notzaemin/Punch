import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Dependency.Kotlin.Version
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("io.papermc.paperweight.userdev") version "1.3.9"
}
repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public")
}
dependencies {
    paperDevBundle("${Dependency.Paper.Version}-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:${Dependency.Paper.Version}-R0.1-SNAPSHOT")
    Dependency.Dependencies.forEach {
        compileOnly(it)
    }
}
bukkit {
    name = rootProject.name
    version = "0.0.1"
    apiVersion = Dependency.Paper.APIVersion
    main = "io.github.notzaemin.${rootProject.name.toLowerCase()}.Main"
    libraries = Dependency.Libraries
}
tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
        filteringCharset = "UTF-8"
    }
    register<Jar>("output") {
        archiveBaseName.set(project.name)
        archiveVersion.set("")
        archiveClassifier.set("")
        from(sourceSets["main"].output)
        doLast {
            copy {
                from(archiveFile)
                into(File(rootDir, ".output"))
            }
        }
    }
    register<Jar>("paperTest") {
        archiveBaseName.set(project.name)
        archiveVersion.set("")
        archiveClassifier.set("")
        from(sourceSets["main"].output)
        doLast {
            copy {
                from(archiveFile)
                val dir = File(rootDir, ".server/plugins")
                into(if (File(dir, archiveFileName.get()).exists()) File(dir, "update") else dir)
            }
        }
    }
}