package com.dansdev.libeventpipe

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EventPipe private constructor() : CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.Default + job

    companion object {

        const val TAG = "EventPipe"

        private var instance: EventPipe? = null
            get() = if (field == null) {
                field = EventPipe()
                field
            } else {
                field
            }

        @ObsoleteCoroutinesApi
        fun <T> registerEvent(
            contextName: String,
            eventDispatcher: CoroutineDispatcher,
            eventClass: Class<T>,
            eventCallback: (T) -> Unit
        ) {
            instance?.registerEvent(contextName, eventDispatcher, eventClass, eventCallback)
        }

        @ObsoleteCoroutinesApi
        fun send(event: Any, delaySend: Long = 0) {
            if (delaySend > 0) {
                instance?.launch {
                    delay(delaySend)
                    instance?.send(event)
                }
            } else {
                instance?.send(event)
            }
        }

        @ObsoleteCoroutinesApi
        fun unregisterAllEvents() {
            instance?.unregisterAllEvent()
        }

        @ObsoleteCoroutinesApi
        fun unregisterByContext(contextName: String) {
            instance?.unregisterByContext(contextName)
        }
    }

    @ObsoleteCoroutinesApi
    private val contextList = mutableMapOf<String, MutableMap<Class<*>, PipeData<*>>>()

    @ObsoleteCoroutinesApi
    private fun send(event: Any) {
        val cloneContextList = mutableMapOf<String, MutableMap<Class<*>, PipeData<*>>>()
        cloneContextList.putAll(contextList)
        for ((_, pipe) in cloneContextList) {
            pipe.keys.firstOrNull { it == event.javaClass || it == event.javaClass.superclass }
                .let { key ->
                    pipe[key]?.send(event)
                }
        }
    }

    @ObsoleteCoroutinesApi
    private fun <T> registerEvent(
        contextName: String,
        eventDispatcher: CoroutineDispatcher,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit
    ) {
        val pipeList = if (contextList.containsKey(contextName)) {
            contextList[contextName]!!
        } else {
            val eventPipe = mutableMapOf<Class<*>, PipeData<*>>()
            contextList[contextName] = eventPipe
            eventPipe
        }
        val eventData = PipeData(this, eventDispatcher, eventCallback)
        pipeList[eventClass] = eventData
    }

    @ObsoleteCoroutinesApi
    private fun unregisterAllEvent() {
        System.out.println("$TAG: unregisterAllEvent()")
        coroutineContext.cancelChildren()
        for ((_, pipe) in contextList) {
            pipe.values.forEach { it.cancel() }
            pipe.clear()
        }
        contextList.clear()
    }

    @ObsoleteCoroutinesApi
    private fun unregisterByContext(contextName: String) {
        System.out.println("$TAG: unregisterByContext(context: $contextName)")
        val cloneContextList = mutableMapOf<String, MutableMap<Class<*>, PipeData<*>>>()
        cloneContextList.putAll(contextList)
        val pipesInContext = cloneContextList.filter { it.key == contextName }
        for ((_, pipe) in pipesInContext) {
            pipe.values.forEach { it.cancel() }
            pipe.clear()
        }
        contextList.remove(contextName)
    }
}