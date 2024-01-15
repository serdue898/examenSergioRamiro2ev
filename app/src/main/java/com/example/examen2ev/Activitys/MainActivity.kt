package com.example.examen2ev.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import com.example.examen2ev.Configuracion
import com.example.examen2ev.Fragments.Fragment1
import com.example.examen2ev.Fragments.Fragmnt2
import com.example.examen2ev.R
import com.example.examen2ev.base.Conexion

class MainActivity : AppCompatActivity() {
    companion object {
        var configuracion = null as Configuracion?
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        configuracion = Configuracion(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar2)
        toolbar.inflateMenu(R.menu.menu)
        setSupportActionBar(toolbar)

        val fragment1 = Fragment1()
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor, fragment1)
            .commit()
        var fragment2 = Fragmnt2()
        if (savedInstanceState != null) {
            fragment2.arguments = savedInstanceState.getBundle("fragment2")

        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor2, fragment2)
            .commit()
        val botonAnadir = findViewById<Button>(R.id.bt_anadir)
        val botonCompletada = findViewById<Button>(R.id.bt_completadas)
        botonAnadir.setOnClickListener {
           Intent(this, Activity2::class.java).also {
               startActivity(it)
           }
        }
        botonCompletada.setOnClickListener {
            Intent(this, Activity3::class.java).also {
                startActivity(it)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mItm_texto17 -> {
                configuracion?.asignartamaño(true)
                val conexion = Conexion(this)
                val tareas = conexion.obtenerTareasPendientes()
                val fragmento = supportFragmentManager.findFragmentById(R.id.contenedor) as? Fragment1
                fragmento?.actualizarLista(tareas)
                true
            }
            R.id.mItm_texto24 -> {
                configuracion?.asignartamaño(false)
                val conexion = Conexion(this)
                val tareas = conexion.obtenerTareasPendientes()
                val fragmento = supportFragmentManager.findFragmentById(R.id.contenedor) as? Fragment1
                fragmento?.actualizarLista(tareas)

                true
            }




            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val fragment2 = supportFragmentManager.findFragmentById(R.id.contenedor2) as? Fragmnt2
        outState.putBundle("fragment2", fragment2?.arguments)
        outState.putString("textoGuardado", fragment2?.tarea2?.id.toString())
    }

}