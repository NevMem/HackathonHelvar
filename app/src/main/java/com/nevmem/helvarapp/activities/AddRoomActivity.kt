package com.nevmem.helvarapp.activities

import android.os.Bundle
import android.view.View
import com.nevmem.helvarapp.R
import com.nevmem.helvarapp.permissions.ListenableActivity

class AddRoomActivity : ListenableActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_room_activity)

        window.statusBarColor = resources.getColor(R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}