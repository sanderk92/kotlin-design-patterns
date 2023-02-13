package com.example.patterns.visitor

import java.util.*

interface Dossier {
    fun accept(visitor: Visitor)
}

data class OpenDossier(
    val id: UUID,
): Dossier {
    override fun accept(visitor: Visitor) {
        visitor.visitOpen(this)
    }
}

data class ClosedDossier(
    val id: UUID,
): Dossier {
    override fun accept(visitor: Visitor) {
        visitor.visitClosed(this)
    }
}

interface Visitor {
    fun export(vararg dossiers: Dossier)
    fun visitOpen(dossier: Dossier)
    fun visitClosed(dossier: Dossier)
}

class DossierXmlExporter: Visitor {
    override fun export(vararg dossiers: Dossier) {
        dossiers.forEach { it.accept(this) }
    }

    override fun visitOpen(dossier: Dossier) {
        println("Visited XML exporter open dossier")
    }

    override fun visitClosed(dossier: Dossier) {
        println("Visited XML exporter closed dossier")
    }
}

class DossierJsonExporter: Visitor {
    override fun export(vararg dossiers: Dossier) {
        dossiers.forEach { it.accept(this) }
    }

    override fun visitOpen(dossier: Dossier) {
        println("Visited JSON exporter open dossier")
    }

    override fun visitClosed(dossier: Dossier) {
        println("Visited JSON exporter closed dossier")
    }
}

fun main() {
    val openDossier = OpenDossier(UUID.randomUUID())
    val closedDossier = ClosedDossier(UUID.randomUUID())

    val exporter = DossierXmlExporter()
    exporter.export(openDossier, closedDossier)
}

