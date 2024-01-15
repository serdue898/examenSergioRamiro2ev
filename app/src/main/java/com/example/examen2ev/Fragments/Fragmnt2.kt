package com.example.examen2ev.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.examen2ev.R
import com.example.examen2ev.base.Tarea

class Fragmnt2 : Fragment() {
    var tarea2 :Tarea? = null
    private var comunicador: Comunicador? = null
    private var contexto: Context? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detalle, container, false)
        contexto = container?.context


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (tarea2 != null) {
            actualizarDetalle(tarea2!!)
            tarea2 = null
        }
    }
    fun actualizarDetalle(tarea: Tarea) {
        this.tarea2 = tarea
        val nombre = view?.findViewById<TextView>(R.id.tx_nombre)
        val descripcion = view?.findViewById<TextView>(R.id.tx_descripcion)
        val fecha = view?.findViewById<TextView>(R.id.tx_fecha)
        nombre?.text = tarea?.nombre
        descripcion?.text = tarea?.descripcion
        fecha?.text = tarea?.fecha
    }


}