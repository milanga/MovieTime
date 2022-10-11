# MovieTime 🎥

MovieTime is a personal project that I use to develop architectural ideas and to learn about new technologies. Thus, it evolving and constantly changing.
The app connects to [TMDB](https://www.themoviedb.org/) API and displays Movies and Series info.

### Home
![MovieTime Home](readme/MovieTimeHome.gif)        

### Detail
![MovieTime Detail](readme/MovieTimeDetail.gif)

## About the app
MovieTime is being developed, using:
* [Jetpack Compose](https://developer.android.com/jetpack/compose) and [Material 3](https://m3.material.io/) to build the UI
* [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) for asynchronous operations
* [Dagger Hilt](https://dagger.dev/hilt/) for dependency injection
* [Coil](https://github.com/coil-kt/coil) for image loading
* [Accompanist](https://github.com/google/accompanist) for some utils for compose (i.e. navigation animations)

## Setup
In order to compile the project a V3 API key is required. To be able to do so, you can create an account in [TMDB](https://www.themoviedb.org/) and then go to your [profile settings](https://www.themoviedb.org/settings/api).
Once you have the API key, you can set it down in your `gradle.properties`
```
TMDB_API_KEY="YOUR_TMDB_KEY"
```

## Architecture
I follow [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) principles where high policy layers don't depend on details. 
This allows me to easily change the UI or the data sources without affecting the domain.

![Clean architecture](readme/architecture.png)

## Roadmap
* Add Unit test
* Add screen previews
* Model repository Result
* Add Room

## License

```
Copyright 2022 Gustavo Torrecilla

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
