package com.example.examen2ev.Activitys

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.examen2ev.AlarmaManager
import com.example.examen2ev.R
import com.example.examen2ev.base.Conexion
import java.time.LocalDateTime
import java.util.Calendar

class Activity2: AppCompatActivity() {
    var tipo ="crear"
    var hora = 0
    var minuto = 0
    var fecha = ""
    var anio = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2)
        val nombre = findViewById<EditText>(R.id.et_nokmbre)
        val descripcion = findViewById<EditText>(R.id.et_descripcion)
        val botonAnadir = findViewById<Button>(R.id.bt_guardar)
        val fecha = findViewById<EditText>(R.id.et_fecha)
        val hora = findViewById<EditText>(R.id.et_hora)
        val completada = findViewById<CheckBox>(R.id.ch_completado)
        val botonLinmpiar = findViewById<Button>(R.id.bt_limpiar)
        if (savedInstanceState != null) {
            nombre.setText(savedInstanceState.getString("nombre"))
            descripcion.setText(savedInstanceState.getString("descripcion"))
            fecha.setText(savedInstanceState.getString("fecha"))
            hora.setText(savedInstanceState.getString("hora"))
            completada.isChecked = savedInstanceState.getBoolean("completada")
        }
        LocalDateTime.now().let {
            fecha.setText(it.year.toString()+"/"+it.monthValue.toString()+"/"+it.dayOfMonth.toString())
            hora.setText(  it.hour.toString()+":"+it.minute.toString())


        }
        // si recibe de un intent un id de tarea es que es para editar
        if (intent.hasExtra("id_tarea")) {
            tipo = "editar"
            val id = intent.getIntExtra("id_tarea", 0)

            completada.isEnabled = true
            val conexion = Conexion(this)
            val tarea = conexion.obtenerTarea(id)
            nombre.setText(tarea.nombre)
            descripcion.setText(tarea.descripcion)
            fecha.setText(tarea.fecha)
            completada.isChecked = tarea.completada
        }
        // añado o actualizo dependiendo del modo
        botonAnadir.setOnClickListener {
            val conexion = Conexion(this)
            val tarea = com.example.examen2ev.base.Tarea(0, nombre.text.toString(), descripcion.text.toString(), fecha.text.toString(), completada.isChecked)
            if (tipo == "crear") {
                conexion.insertarTarea(tarea)
            } else {
                val id = intent.getIntExtra("id", 0)
                conexion.actualizarTarea(id, nombre.text.toString(), descripcion.text.toString(), fecha.text.toString(), completada.isChecked)
            }
             val alarma = AlarmaManager( this, fecha.text.toString(), hora.text.toString(),nombre.text.toString())
            alarma.establecerAlarma()
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
        botonLinmpiar.setOnClickListener {
            nombre.setText("")
            descripcion.setText("")
            fecha.setText("")
            completada.isChecked = false
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val nombre = findViewById<EditText>(R.id.et_nokmbre)
        val descripcion = findViewById<EditText>(R.id.et_descripcion)
        val fecha = findViewById<EditText>(R.id.et_fecha)
        val hora = findViewById<EditText>(R.id.et_hora)
        val completada = findViewById<CheckBox>(R.id.ch_completado)
        outState.putString("nombre", nombre.text.toString())
        outState.putString("descripcion", descripcion.text.toString())
        outState.putString("fecha", fecha.text.toString())
        outState.putString("hora", hora.text.toString())
        outState.putBoolean("completada", completada.isChecked)
    }



}