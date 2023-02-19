package com.example.patterns.behavioral.state

import java.time.Instant
import java.util.*

data class DossierId(val value: UUID)

interface CaseContext
data class CivilCase(val id: DossierId): CaseContext

data class CourtRuling (val message: String)

sealed interface Dossier<Case> {
    val case: Case
}

data class OpenDossier<Case: CaseContext>(
    override val case: Case,
) : Dossier<Case> {
    fun assess(time: Instant) = ProcessingDossier(case, time)
}

data class ProcessingDossier<Case: CaseContext>(
    override val case: Case,
    val started: Instant,
) : Dossier<Case> {
    fun finalize(ruling: CourtRuling) = FinalizedDossier(case, started, ruling)
}

data class FinalizedDossier<Case: CaseContext>(
    override val case: Case,
    val started: Instant,
    val ruling: CourtRuling,
) : Dossier<Case> {
    fun reopen() = OpenDossier(case)
    fun reassess(time: Instant) = ProcessingDossier(case, time)
}
