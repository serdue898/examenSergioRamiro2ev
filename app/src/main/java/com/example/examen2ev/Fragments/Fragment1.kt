package com.example.examen2ev.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2ev.R
import com.example.examen2ev.adapters.TareasPendientesAdapter
import com.example.examen2ev.base.Conexion
import com.example.examen2ev.base.Tarea

class Fragment1 : Fragment() {
    private var contexto: Context? = null
    private var comunicador: Comunicador? = Comunicador()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment1, container, false)
        contexto = container?.context


        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val conexion = Conexion(contexto!!)
        conexion.insertarTarea(Tarea(1, "Descripcion 1", "patata","quso",false))
        val tareas = conexion.obtenerTareasPendientes()
        actualizarLista(tareas)

    }

    fun actualizarLista(partidas: List<Tarea>) {
        val lista = view?.findViewById<RecyclerView>(R.id.rv_info)
        lista?.layoutManager = LinearLayoutManager(contexto)
        val dividerItemDecoration = DividerItemDecoration(lista?.context, (lista?.layoutManager as LinearLayoutManager).orientation)
        lista?.addItemDecoration(dividerItemDecoration)
        val adapter = TareasPendientesAdapter(partidas, contexto!! , comunicador!!,this)
        lista?.adapter = adapter


    }
}