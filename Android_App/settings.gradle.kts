pluginManagement {
    repositories {
        google {
            content {
                // Only include Google and Android-related plugins
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()          // Maven Central for other plugins
        gradlePluginPortal()    // Gradle Plugin Portal for standard plugins
        maven("https://jitpack.io") // JitPack for GitHub-hosted plugins
    }
}

dependencyResolutionManagement {
    // Prevent modules from defining their own repositories
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()                 // Android libraries
        mavenCentral()           // Standard libraries
        maven("https://jitpack.io") // JitPack for dependencies from GitHub
    }
}

// Root project name
rootProject.name = "BirrWise"

// Include modules
include(":app")
