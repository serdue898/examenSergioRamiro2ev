package com.example.examen2ev.Activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2ev.Fragments.Comunicador
import com.example.examen2ev.R
import com.example.examen2ev.adapters.TareasCompletadasAdapter
import com.example.examen2ev.adapters.TareasPendientesAdapter
import com.example.examen2ev.base.Conexion
import com.example.examen2ev.base.Tarea

class Activity3: AppCompatActivity() {
    val comunicador = Comunicador()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity3)
        val conexion = Conexion(this)
        val tareas = conexion.obtenerTareasCompletadas()
        actualizarLista(tareas)
        val botonVolver = findViewById<Button>(R.id.bt_volver)
        botonVolver.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    fun actualizarLista(partidas: List<Tarea>) {
        val lista = findViewById<RecyclerView>(R.id.rv_completadas)
        lista?.layoutManager = LinearLayoutManager(this)

        val adapter = TareasCompletadasAdapter(partidas, this , comunicador!!)
        lista?.adapter = adapter


    }
}