package com.example.patterns.decorator

import java.time.Instant
import java.util.*

sealed interface Dossier {
    fun view()
}

data class CommonLawDossier(
    val id: UUID,
    val user: String,
    val created: Instant,
) : Dossier {
    override fun view() = println(this)
}

data class CivilLawDossier(
    val id: UUID,
    val user: String,
    val created: Instant,
) : Dossier {
    override fun view() = println(this)
}

interface DossierStateDecorator : Dossier {
    val dossier: Dossier
}

data class OpenDossier(
    override val dossier: Dossier,
) : DossierStateDecorator {
    fun run() = RunningDossier(dossier)
    override fun view() = dossier.view()
}

data class RunningDossier(
    override val dossier: Dossier,
) : DossierStateDecorator {
    fun succeed() = SuccessDossier(dossier)
    override fun view() = dossier.view()
}

data class SuccessDossier(
    override val dossier: Dossier,
) : DossierStateDecorator {
    fun reopen() = OpenDossier(dossier)
    fun rerun() = RunningDossier(dossier)
    override fun view() = dossier.view()
}

data class FailureDossier(
    override val dossier: Dossier,
    val message: String,
) : DossierStateDecorator {
    override fun view() = dossier.view()
}
