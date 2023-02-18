package com.example.patterns.structural.bridge

interface Color {
    val string: String
}

interface Shape {
    fun getColor(): String
}

data class Red(
    override val string: String
): Color

data class Blue(
    override val string: String
): Color

data class Square(
    val color: Color
): Shape {
    override fun getColor() = color.string
}

data class Circle(
    val color: Color
): Shape {
    override fun getColor() = color.string
}