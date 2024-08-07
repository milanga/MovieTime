pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MovieTime"
include(":app")
include(":core:presentation")
include(":movie:presentation:home")
include(":movie:presentation:detail")
include(":core:views")
include(":home:presentation")
include(":data:tmdb")
include(":data:trakt")
include(":data:local")
include(":data:memory")
include("domain")
include(":tvshow:presentation:home")
include(":tvshow:presentation:detail")
include(":search:presentation:home")
include(":auth:presentation")