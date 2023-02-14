package com.example.patterns.creational.singleton

class Subject

class Singleton private constructor() {

    companion object {

        @Volatile
        private var subject: Subject? = null

        @Synchronized
        fun getSubject(): Subject {
            if (subject == null) {
                subject = Subject()
            }
            return subject!!
        }
    }
}

fun main() {
    val subject = Singleton.getSubject()
    println(subject)
}
