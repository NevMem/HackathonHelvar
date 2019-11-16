package com.nevmem.helvarapp.data

import android.util.Log
import com.nevmem.helvar.network.WifiAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class RoomRepository @Inject constructor(private val wifiAdapter: WifiAdapter) {
    private var rooms = ArrayList<Room>()

    val currentRoom = BehaviorSubject.create<Room>()
    val allRooms = BehaviorSubject.create<List<Room>>()

    init {
        Observable.combineLatest(
            wifiAdapter.scanner.scanResults,
            allRooms,
            BiFunction<List<Pair<Int, String>>, List<Room>, Pair<List<Pair<Int, String>>, List<Room>>>
                { first, second -> Pair(first, second) })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.second
                    .minBy { room -> room.calculateProfileSimilarity(
                        it.first.map { Pair(it.second, it.first.toDouble()) }) }
                    ?.let { room -> currentRoom.onNext(room) }
            }

    }

    fun addRoom(room: Room) {
        val newRooms = ArrayList<Room>()
        newRooms.addAll(rooms)
        Log.d("debug", "Adding new room")
        newRooms.add(room)
        rooms = newRooms
        allRooms.onNext(rooms)
    }
}