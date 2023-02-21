package com.example.patterns.custom.workflow

import java.time.Instant
import java.util.*

class StateRepository {

    private val store: MutableList<CivilLawDossierEntity> = mutableListOf()

    fun store(dossier: Dossier<CivilCase>) {
        store.add(dossier.asDossierEntity())
    }

    fun update(dossier: Dossier<CivilCase>) {
        store.removeAll { it.id == dossier.case.id.value }
        store.add(dossier.asDossierEntity())
    }

    fun find(dossierId: DossierId): Dossier<CivilCase>? =
        store
            .firstOrNull { it.id == dossierId.value }
            ?.let(CivilLawDossierEntity::asDossierModel)

    fun findOpenDossiers(): List<OpenDossier<CivilCase>> =
        store
            .filter { it.state == State.Open }
            .map(CivilLawDossierEntity::asOpenDossierModel)

    fun findProcessingDossiers(): List<ProcessingDossier<CivilCase>> =
        store
            .filter { it.state == State.Processing }
            .map(CivilLawDossierEntity::asProcessingDossierModel)

    fun findFinalizedDossiers(): List<FinalizedDossier<CivilCase>> =
        store
            .filter { it.state == State.Finalized }
            .map(CivilLawDossierEntity::asFinalizedDossierModel)
}

private fun Dossier<CivilCase>.asDossierEntity(): CivilLawDossierEntity = when (this) {
    is OpenDossier -> asOpenDossierEntity()
    is ProcessingDossier -> asProcessingDossierEntity()
    is FinalizedDossier -> asFinalizedDossierEntity()
}

private fun OpenDossier<CivilCase>.asOpenDossierEntity() = CivilLawDossierEntity(
    id = case.id.value,
    state = State.Open,
    started = null,
    ruling = null,
)

private fun ProcessingDossier<CivilCase>.asProcessingDossierEntity() = CivilLawDossierEntity(
    id = case.id.value,
    state = State.Processing,
    started = started,
    ruling = null,
)

private fun FinalizedDossier<CivilCase>.asFinalizedDossierEntity() = CivilLawDossierEntity(
    id = case.id.value,
    state = State.Finalized,
    started = started,
    ruling = ruling,
)

private fun CivilLawDossierEntity.asDossierModel(): Dossier<CivilCase> = when (this.state) {
    State.Open -> asOpenDossierModel()
    State.Processing -> asProcessingDossierModel()
    State.Finalized -> asFinalizedDossierModel()
}

private fun CivilLawDossierEntity.asOpenDossierModel() = OpenDossier(
    case = extractContext(),
)

private fun CivilLawDossierEntity.asProcessingDossierModel() = ProcessingDossier(
    case = extractContext(),
    started = started!!
)

private fun CivilLawDossierEntity.asFinalizedDossierModel() = FinalizedDossier(
    case = extractContext(),
    started = started!!,
    ruling = ruling!!,
)

private fun CivilLawDossierEntity.extractContext() = CivilCase(
    id = DossierId(id)
)

private data class CivilLawDossierEntity(
    val id: UUID,
    val state: State,
    val started: Instant?,
    val ruling: CourtRuling?,
)

private enum class State {
    Open,
    Processing,
    Finalized,
}

fun main() {
    val repo = StateRepository()

    val open = OpenDossier(CivilCase(DossierId(UUID.randomUUID())))
    repo.store(open)
    repo.printAll()

    val processing = open.process(Instant.now())
    repo.update(processing)
    repo.printAll()

    val finalized = processing.finalize(CourtRuling("Case denied"))
    repo.update(finalized)
    repo.printAll()
}

private fun StateRepository.printAll() {
    println("Printing all dossiers!")
    println(findOpenDossiers())
    println(findProcessingDossiers())
    println(findFinalizedDossiers())
    println()
}
