architectury {
    neoForge()
}

loom {
    runs {
        create("data") {
            data()
            programArgs("--all", "--mod", "endermanoverhaul")
            programArgs("--output", project(":common").file("src/main/generated/resources").absolutePath)
            programArgs("--existing", project(":common").file("src/main/resources").absolutePath)
        }
    }
}

val common: Configuration by configurations.creating {
    configurations.compileClasspath.get().extendsFrom(this)
    configurations.runtimeClasspath.get().extendsFrom(this)
    configurations["developmentNeoForge"].extendsFrom(this)
}

dependencies {
    common(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    shadowCommon(project(path = ":common", configuration = "transformProductionNeoForge")) {
        isTransitive = false
    }

    val neoforgeVersion: String by project
    val minecraftVersion: String by project
    val mekanismVersion: String by project

    neoForge(group = "net.neoforged", name = "neoforge", version = neoforgeVersion)
    forgeRuntimeLibrary("com.eliotlash.mclib:mclib:20")

    modLocalRuntime(group = "maven.modrinth", name = "mekanism", version = "10.5.8.30")
    modLocalRuntime(group = "maven.modrinth", name = "mekanism-additions", version = "10.5.8.30")
}
