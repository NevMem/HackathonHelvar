package com.nevmem.helvarapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialogFragment
import com.nevmem.helvarapp.R

class SavingDialog : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.DialogCustomTheme)
        val view = LayoutInflater.from(activity).inflate(R.layout.saving_dialog, null, false)
        return builder.setView(view)
            .create()
    }
}