package com.example.examen2ev

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class Configuracion( context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("Configuracion", Context.MODE_PRIVATE)
    fun asignartamaño(si : Boolean) {
        prefs.edit().putBoolean("texto17", si).apply()

    }
    fun cogerTamaño(): Int {
        return if (prefs.getBoolean("texto17", true)) {
            17
        } else {
            24
        }
    }

}