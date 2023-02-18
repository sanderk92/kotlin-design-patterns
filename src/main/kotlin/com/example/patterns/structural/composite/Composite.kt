package com.example.patterns.structural.composite

interface Node {
    fun build(): String
}

data class Branch(
    val children: List<Node>,
) : Node {
    constructor(vararg children: Node): this(children.toList())
    override fun build() = children.joinToString(
        prefix = "(",
        postfix = ")",
        separator = ",",
        transform = Node::build
    )
}

data class Leaf(
    val value: String,
) : Node {
    override fun build(): String = value
}

fun main() {
    val composite = Branch(
        Branch(
            Leaf("a"),
            Leaf("b"),
        ),
        Branch(
            Leaf("c"),
            Leaf("d"),
        )
    )

    println(composite.build())
}