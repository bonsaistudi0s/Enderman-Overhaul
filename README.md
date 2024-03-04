# Enderman Overhaul

To add this library to your project, do the following:

Kotlin DSL:
```kotlin
repositories {
    maven(url = "https://maven.teamresourceful.com/repository/maven-public/")
}

dependencies {
    "modImplementation"(group = "tech.alexnijjar.endermanoverhaul", name = "endermanoverhaul-$modLoader-$minecraftVersion", version = endermanOverhaulVersion)
}
```

Groovy DSL:
```groovy
repositories {
    maven {
        url "https://maven.teamresourceful.com/repository/maven-public/"
    }
}

dependencies {
    "modImplementation" group: "tech.alexnijjar.endermanoverhaul", name: "endermanoverhaul-$modLoader-$minecraftVersion", version: endermanOverhaulVersion
}
```

Author: @Alex
CurseForge: https://www.curseforge.com/minecraft/mc-mods/argonauts
Modrinth: https://modrinth.com/mod/argonauts
Source: https://github.com/terrarium-earth/argonauts
Discord: https://discord.gg/terrarium

A guild and party mod to work and play together with your teammates on a server! 