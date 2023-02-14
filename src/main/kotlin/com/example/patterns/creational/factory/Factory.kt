package com.example.patterns.creational.factory

interface Dossier
class CommonLawDossier: Dossier
class CivilLawDossier: Dossier

interface DossierProcessor {
    fun create(): Dossier
}

class CommonLawDossierProcessor: DossierProcessor {
    override fun create() = CommonLawDossier()
}

class CivilLawDossierProcessor: DossierProcessor {
    override fun create() = CivilLawDossier()
}
