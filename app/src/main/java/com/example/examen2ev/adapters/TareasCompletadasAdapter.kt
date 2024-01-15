package com.example.examen2ev.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2ev.Activitys.Activity2
import com.example.examen2ev.Fragments.Comunicador
import com.example.examen2ev.R
import com.example.examen2ev.base.Tarea

class TareasCompletadasAdapter(private val tareas: List<Tarea>, var contexto : Context, val comunicador : Comunicador) : RecyclerView.Adapter<TareasCompletadasAdapter.PostViewHolder>() {

    // Definición del ViewHolder que proporciona una referencia a las vistas para cada elemento de datos.
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombre: TextView = itemView.findViewById(R.id.tx_nombre)
        var id: TextView = itemView.findViewById(R.id.tx_id)
        var finalizada: TextView = itemView.findViewById(R.id.tx_fecha)

    }

    // Creación de nuevas vistas (invocadas por el layout manager).
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listapendientes, parent, false)
        return PostViewHolder(view)
    }

    // Reemplazo del contenido de una vista (invocado por el layout manager).
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        if (position == 0) {
            // Configurar encabezados
            holder.id.text = "ID"
            holder.nombre.text = "nombre"
            holder.finalizada.text = "fecha"
        } else {
            val tarea = tareas[position - 1]  // Resta 1 para compensar el encabezado
            holder.id.text =tarea.id.toString()
            holder.nombre.text = tarea.nombre
            holder.finalizada.text = tarea.fecha

            // Agregar un clic listener para cargar la partida
            holder.itemView.setOnClickListener {
                comunicador.actualizarDetalle(contexto,tarea)

            }
            holder.itemView.setOnLongClickListener {
                val popup = AlertDialog.Builder(contexto)
                popup.setTitle("¿Desea eliminar la tarea?")
                val view = LayoutInflater.from(contexto).inflate(R.layout.popup_contextual, null)
                popup.setView(view)
                val completar = view.findViewById<Button>(R.id.bt_Completar)
                val eliminar = view.findViewById<Button>(R.id.bt_Eliminar)
                val modificar = view.findViewById<Button>(R.id.bt_Modificar)
                val enviar = view.findViewById<Button>(R.id.bt_Enviar)
                enviar.visibility = View.GONE
                completar.visibility = View.GONE
                eliminar.visibility = View.GONE
                modificar.setOnClickListener{
                    Intent(contexto, Activity2::class.java).apply {
                        putExtra("tarea", tarea.id)
                        contexto.startActivity(this)
                    }
                }
                true
            }
        }
    }

    // Devuelve el tamaño de tu conjunto de datos (invocado por el layout manager).
    override fun getItemCount() = tareas.size+1
}