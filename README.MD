### Library EventPipe similar to EventBus
-------------

This library base on [Kotlin Coroutines Channel](https://kotlinlang.org/docs/reference/coroutines/channels.html).

###### 1. Implementation by Gradle. [![](https://jitpack.io/v/Daniil-Pavenko/coroutines-event-pipe.svg)](https://jitpack.io/#Daniil-Pavenko/coroutines-event-pipe)
```
implementation 'com.github.Daniil-Pavenko:coroutines-event-pipe:1.0.0'
```

###### 2. Methods for using:

- Register event class:
```
EventPipe.registerEvent(contextName: String, eventDispatcher: CoroutineDispatcher, eventClass: Class<T>, eventCallback: (T) -> Unit)
```
- And unregister into `onDestroy` or `onStop` by your logic:
```
EventPipe.unregisterAllEvents() - for all Events

or

EventPipe.unregisterByContext(contextName: String)
```
- Send event:
```
EventPipe.send(event: Any, delaySend: Long = 0)
```


------------

###### Please, starred lib, if you like it.