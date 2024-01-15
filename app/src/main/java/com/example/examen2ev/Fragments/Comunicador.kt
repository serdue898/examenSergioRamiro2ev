package com.example.examen2ev.Fragments

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.examen2ev.R
import com.example.examen2ev.base.Tarea

class Comunicador {
    fun actualizarDetalle(contexto:Context,tarea: Tarea) {
        val fragment2 = Fragmnt2()
        fragment2.actualizarDetalle(tarea)
        if (contexto is AppCompatActivity) {

            val fragmentManager = contexto.supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.contenedor2, fragment2)
                .commit()

        }
    }
}