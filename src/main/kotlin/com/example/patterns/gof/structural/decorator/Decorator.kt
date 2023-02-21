package com.example.patterns.gof.structural.decorator

interface Drawing {
    fun draw()
}

class Square : Drawing {
    override fun draw() = print("[]")
}

class Dashed(private val drawing: Drawing): Drawing {
    override fun draw() {
        print("-")
        drawing.draw()
        print("-")
    }
}

class Escaped(private val drawing: Drawing): Drawing {
    override fun draw() {
        print("\\")
        drawing.draw()
        print("\\")
    }
}

fun main() {
    val square = Square()
    square.draw()
    println()

    val dashedSquare = Dashed(square)
    dashedSquare.draw()
    println()

    val escapedDashedSquare = Escaped(dashedSquare)
    escapedDashedSquare.draw()
    println()
}