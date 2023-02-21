package com.example.patterns.gof.behavioral.state

data class Phone(
    var soundState: SoundState = Music
) {
    fun call() {
        soundState.call()
    }

    fun setState(soundState: SoundState) {
        this.soundState = soundState
    }

}

interface SoundState {
    fun call()
}

object Silent: SoundState {
    override fun call() = println("...")
}

object Vibrate: SoundState {
    override fun call() = println("Brrrr ...")
}

object Music: SoundState {
    override fun call() = println("Ladadi ...")
}

fun main() {
    val phone = Phone()
    phone.call()

    phone.setState(Vibrate)
    phone.call()

    phone.setState(Silent)
    phone.call()
}