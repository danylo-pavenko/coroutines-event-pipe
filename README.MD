### Library EventPipe similar to EventBus
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Coroutines%20Event%20Pipe-brightgreen.svg?style=flat-square)](https://android-arsenal.com/details/1/7797)
-------------
##### Old version
This library base on [Kotlin Coroutines Channel](https://kotlinlang.org/docs/reference/coroutines/channels.html).
#### New version
Version 2.0 and next based on [Kotlin Coroutines SharedFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-shared-flow/)

###### 1. Implementation by Gradle. [![](https://jitpack.io/v/Daniil-Pavenko/coroutines-event-pipe.svg)](https://jitpack.io/#Daniil-Pavenko/coroutines-event-pipe)
```
implementation 'com.github.Daniil-Pavenko:coroutines-event-pipe:2.0.0'
```

###### 2. Methods for using:

- Register event class:
```kotlin
EventPipe.registerEvent(
    eventClass: Class<T>,
    contextName: String = GLOBAL_CONTEXT, // default
    eventDispatcher: CoroutineDispatcher = Dispatchers.Main, // default
    eventCallback: suspend (event: T) -> Unit
)
```
- And unregister into `onDestroy` or `onStop` by your logic:
```kotlin
EventPipe.unregisterAllEvents() - for all Events

or

EventPipe.unregisterByContext(contextName: String)
```
- Send event:
```kotlin
EventPipe.send(event: Any, delaySend: Long = 0)
```

##### 3. Configure proguard rules.
- Add package of lib.
- Add package with Event classes. 
```kotlin
#EventPipe
-keep class com.dansdev.libeventpipe.** { *; }
```

------------

###### Please, starred lib, if you like it.
