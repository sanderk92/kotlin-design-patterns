package com.example.patterns.behavioral.visitor

import java.util.*

interface Dossier {
    fun accept(exporter: DossierExporter)
}

data class OpenDossier(
    val id: UUID,
): Dossier {
    override fun accept(exporter: DossierExporter) {
        exporter.exportOpen(this)
    }
}

data class ClosedDossier(
    val id: UUID,
): Dossier {
    override fun accept(exporter: DossierExporter) {
        exporter.exportClosed(this)
    }
}

interface DossierExporter {
    fun export(vararg dossiers: Dossier)
    fun exportOpen(dossier: Dossier)
    fun exportClosed(dossier: Dossier)
}

class DossierXmlExporter: DossierExporter {
    override fun export(vararg dossiers: Dossier) {
        dossiers.forEach { it.accept(this) }
    }

    override fun exportOpen(dossier: Dossier) {
        println("Visited XML exporter open dossier")
    }

    override fun exportClosed(dossier: Dossier) {
        println("Visited XML exporter closed dossier")
    }
}

class DossierJsonExporter: DossierExporter {
    override fun export(vararg dossiers: Dossier) {
        dossiers.forEach { it.accept(this) }
    }

    override fun exportOpen(dossier: Dossier) {
        println("Visited JSON exporter open dossier")
    }

    override fun exportClosed(dossier: Dossier) {
        println("Visited JSON exporter closed dossier")
    }
}

fun main() {
    val openDossier = OpenDossier(UUID.randomUUID())
    val closedDossier = ClosedDossier(UUID.randomUUID())

    val exporter = DossierXmlExporter()
    exporter.export(openDossier, closedDossier)
}

