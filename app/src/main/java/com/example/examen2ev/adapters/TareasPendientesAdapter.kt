package com.example.examen2ev.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2ev.Activitys.Activity2
import com.example.examen2ev.Activitys.MainActivity
import com.example.examen2ev.Fragments.Comunicador
import com.example.examen2ev.Fragments.Fragment1
import com.example.examen2ev.R
import com.example.examen2ev.base.Conexion
import com.example.examen2ev.base.Tarea


class TareasPendientesAdapter(private val tareas: List<Tarea>, var contexto : Context , val comunicador : Comunicador,val fragment :Fragment1) : RecyclerView.Adapter<TareasPendientesAdapter.PostViewHolder>() {

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
        holder.id.textSize = MainActivity.configuracion!!.cogerTamaño().toFloat()
        if (position == 0) {
            // Configurar encabezados
            holder.id.text = "ID"
            holder.nombre.text = contexto.getString(R.string.nombre)
            holder.finalizada.text = contexto.getString(R.string.fecha)
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
                popup.setTitle(contexto.getString(R.string.desea_eliminar_la_tarea))
                val view = LayoutInflater.from(contexto).inflate(R.layout.popup_contextual, null)
                popup.setView(view)
                val completar = view.findViewById<Button>(R.id.bt_Completar)
                val eliminar = view.findViewById<Button>(R.id.bt_Eliminar)
                val modificar = view.findViewById<Button>(R.id.bt_Modificar)
                val enviar = view.findViewById<Button>(R.id.bt_Enviar)
                val creado = popup.create()
                enviar.setOnClickListener{


                    //creo intent de correo
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra(Intent.EXTRA_EMAIL, tarea.nombre +""   + tarea.fecha)
                    intent.putExtra(Intent.EXTRA_SUBJECT, tarea.nombre +  "" + tarea.fecha)
                    intent.putExtra(Intent.EXTRA_TEXT, tarea.nombre +  "" + tarea.fecha)
                    intent.type = "message/rfc822"
                    contexto.startActivity(Intent.createChooser(intent,
                        contexto.getString(R.string.contexto)))
                }
                completar.setOnClickListener {
                    //asigno completado a al abase
                    val connexion = Conexion(contexto)
                    connexion.actualizarTarea(tarea.id,tarea.nombre,tarea.descripcion ,tarea.fecha, true)
                    fragment.actualizarLista(connexion.obtenerTareasPendientes())
                    creado.dismiss()

                }
                eliminar.setOnClickListener {
                    //elimino de la base
                    val connexion = Conexion(contexto)
                    connexion.eliminarTarea(tarea.id)
                    fragment.actualizarLista(connexion.obtenerTareasPendientes())
                    creado.dismiss()
                }
                modificar.setOnClickListener{
                    //modifico la tarea
                    Intent(contexto, Activity2::class.java).apply {
                        putExtra("id_tarea", tarea.id)
                        contexto.startActivity(this)
                    }
                    creado.dismiss()
                }
                creado.show()
                true
            }
        }
    }

    // Devuelve el tamaño de tu conjunto de datos (invocado por el layout manager).
    override fun getItemCount() = tareas.size+1
}