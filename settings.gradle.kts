pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "VKCourse"

include(":homework-01", ":homework-03", ":homework-04")
project(":homework-01").projectDir = file("assignments/01-practice")
project(":homework-03").projectDir = file("assignments/03-intents")
project(":homework-04").projectDir = file("assignments/04-compose-app-list")
