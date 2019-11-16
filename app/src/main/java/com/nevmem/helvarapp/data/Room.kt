package com.nevmem.helvarapp.data

import com.nevmem.helvarapp.R

class Room(val name: String) {
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

    val roomId: String
    get () {
        return when (name.toLowerCase()) {
            "living room" -> "0"
            "entrance" -> "1"
            "kitchen" -> "2"
            else -> "3"
        }
    }

    val roomName: String
    get() {
        return when (name.toLowerCase()) {
            "living room" -> "living"
            "entrance" -> "entrance"
            "kitchen" -> "kitchen"
            else -> "fuck"
        }
    }

    val wifiProfile = ArrayList<Pair<String, Double>>()

    fun calculateProfileSimilarity(profile: List<Pair<String, Double>>): Double {
        val names = wifiProfile.map { it.first }.toHashSet() + profile.map { it.first }.toHashSet()
        var similarity = 0.0
        names.forEach { name ->
            var first = 0.0
            wifiProfile.find { name == it.first }?.let { first = it.second }
            var second = 0.0
            profile.find { name == it.first }?.let { second = it.second }
            similarity += (first - second) * (first - second)
        }
        return similarity
    }

    val imageId: Int
    get () {
        return when (name) {
            "Kitchen" -> R.drawable.food
            "Living room" -> R.drawable.tv
            "Entrance" -> R.drawable.walk
            else -> R.drawable.family
        }
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

    var bySensor: Boolean = true

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