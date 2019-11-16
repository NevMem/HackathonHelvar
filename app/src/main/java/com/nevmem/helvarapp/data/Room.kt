package com.nevmem.helvarapp.data

class Room(val name: String, val imageId: Int) {
    enum class RoomMode {
        RELAX, WORK, SLEEP, CUSTOM
    }

    var temperature: Int = 4500
    set (value) {
        if (value == field)
            return
        field = value
        recalcMode()
    }

    var brightness: Int = 50
    set (value) {
        if (value == field)
            return
        field = value
        recalcMode()
    }

    var mode: RoomMode = RoomMode.CUSTOM
    private set

    var bySensor: Boolean = false

    fun modeString(): String {
        return when (mode) {
            RoomMode.CUSTOM -> "Custom preset"
            RoomMode.RELAX -> "Relax mode"
            RoomMode.SLEEP -> "Sleep mode"
            RoomMode.WORK -> "Work mode"
        }
    }

    private fun recalcMode() {
        // TODO: (modes handling)
    }
}