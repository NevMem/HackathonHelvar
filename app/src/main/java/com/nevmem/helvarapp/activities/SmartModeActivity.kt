package com.nevmem.helvarapp.activities

import android.os.Bundle
import com.nevmem.helvarapp.R
import com.nevmem.helvarapp.permissions.ListenableActivity

class SmartModeActivity : ListenableActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.smart_mode_activity)
    }
}