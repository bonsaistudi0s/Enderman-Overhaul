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
