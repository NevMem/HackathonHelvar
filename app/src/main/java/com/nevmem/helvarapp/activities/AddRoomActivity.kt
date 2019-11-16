package com.nevmem.helvarapp.activities

import android.os.Bundle
import com.nevmem.helvarapp.R
import com.nevmem.helvarapp.permissions.ListenableActivity

class AddRoomActivity : ListenableActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_room_activity)
    }
}