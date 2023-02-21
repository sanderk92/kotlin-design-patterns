package com.example.patterns.gof.behavioral.chainofresponsibility

data class Request(
    val value: String,
)

data class Result(
    val message: String,
)

interface Handler {
    val next: Handler?
    fun process(request: Request): Result
}

class ValidationHandler(override val next: Handler? = null) : Handler {
    override fun process(request: Request): Result {
        if (request.value.isEmpty()) {
            return Result("Empty value!")
        }
        println("Validated request!")
        return next?.process(request) ?: Result("Validation succeeded")
    }
}

class CachingHandler(override val next: Handler? = null) : Handler {
    override fun process(request: Request): Result {
        println("Cached request!")
        return next?.process(request) ?: Result("Caching succeeded")
    }
}

fun main() {
    val cachingHandler = CachingHandler()
    val validationHandler = ValidationHandler(cachingHandler)
    validationHandler.process(Request("Test"))
}
