# MovieTime 🎥

MovieTime is a personal project that I use to develop architecture ideas and learn new technologies. Thus it evolves and is in constantly change.
The app connects to [TMDB](https://www.themoviedb.org/) API and displays Movies and Series info.

![MovieTime Home](readme/MovieTimeHome.gif)         ![MovieTime Detail](readme/MovieTimeDetail.gif)

## About the app
MovieTime is developed using:
* [Jetpack Compose](https://developer.android.com/jetpack/compose) to build de UI
* [Material 3](https://m3.material.io/)
* [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) for asynchronous operations
* [Dagger Hilt](https://dagger.dev/hilt/) for dependency injection
* [Coil](https://github.com/coil-kt/coil) for image loading
* [Accompanist](https://github.com/google/accompanist) for some utils for compose (i.e. navigation animations)

## Setup
In order to compile the project a V3 API key is required. To do so, you can create an account in [TMDB](https://www.themoviedb.org/) and then go to your [profile settings](https://www.themoviedb.org/settings/api).
Once you have the API key, you can set them in your `gradle.properties`
```
TMDB_API_KEY="YOUR_TMDB_KEY"
```