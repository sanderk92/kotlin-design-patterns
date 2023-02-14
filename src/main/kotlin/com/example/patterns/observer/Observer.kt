package com.example.patterns.observer

interface DossierProcessor {
    fun process()
}

class DossierExpiryProcessor : DossierProcessor {
    override fun process() = println("Checked dossiers to expire")
}

class DossierRetryProcessor : DossierProcessor {
    override fun process() = println("Checked dossiers to retry")
}

class DossierManager {
    private val processors: MutableList<DossierProcessor> = mutableListOf()

    fun register(observer: DossierProcessor) {
        processors.add(observer)
    }

    fun unregister(observer: DossierProcessor) {
        processors.remove(observer)
    }

    fun process() {
        processors.forEach(DossierProcessor::process)
    }
}

fun main() {
    val manager = DossierManager()
    manager.register(DossierRetryProcessor())
    manager.register(DossierExpiryProcessor())
    manager.process()
}
