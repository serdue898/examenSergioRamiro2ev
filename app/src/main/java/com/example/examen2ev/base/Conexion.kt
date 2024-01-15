package com.example.examen2ev.base

import android.content.ContentValues
import android.content.Context

class Conexion (Context: Context) {
    private val dbHelper: BaseDatos = BaseDatos(Context)
    companion object {
        private const val DATABASE_NAME = "BaseTareas"
        private const val DATABASE_VERSION = 1
        private const val TABLE_TAREAS = "tareas"
        private const val KEY_ID_T = "id_tarea"
        private const val KEY_NOMBRE_T = "nombre"
        private const val KEY_DESCRIPCION_T = "descripcion"
        private const val KEY_FECHA_T = "fecha"
        private const val KEY_COMPLETADA_T = "completada"


    }
    fun obtenerTareasPendientes(): List<Tarea> {
        val listaTareas = ArrayList<Tarea>()
        val selectQuery = "SELECT  * FROM $TABLE_TAREAS WHERE $KEY_COMPLETADA_T = 0"
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex(KEY_ID_T)
            val nombreIndex = cursor.getColumnIndex(KEY_NOMBRE_T)
            val descripcionIndex = cursor.getColumnIndex(KEY_DESCRIPCION_T)
            val fechaIndex = cursor.getColumnIndex(KEY_FECHA_T)
            val completadaIndex = cursor.getColumnIndex(KEY_COMPLETADA_T)
            if (idIndex != -1 && nombreIndex != -1 && descripcionIndex != -1 && fechaIndex != -1 && completadaIndex != -1) {

                val id = cursor.getInt(idIndex)
                val nombre = cursor.getString(nombreIndex)
                val descripcion = cursor.getString(descripcionIndex)
                val fecha = cursor.getString(fechaIndex)
                val completada = cursor.getInt(completadaIndex) == 1

                val tarea = Tarea(id, nombre, descripcion, fecha, completada)
                listaTareas.add(tarea)
            }

        }
        db.close()
        return listaTareas
    }
    fun obtenerTareasCompletadas(): List<Tarea> {
        val listaTareas = ArrayList<Tarea>()
        val selectQuery = "SELECT  * FROM $TABLE_TAREAS WHERE $KEY_COMPLETADA_T = 1"
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex(KEY_ID_T)
            val nombreIndex = cursor.getColumnIndex(KEY_NOMBRE_T)
            val descripcionIndex = cursor.getColumnIndex(KEY_DESCRIPCION_T)
            val fechaIndex = cursor.getColumnIndex(KEY_FECHA_T)
            val completadaIndex = cursor.getColumnIndex(KEY_COMPLETADA_T)
            if (idIndex != -1 && nombreIndex != -1 && descripcionIndex != -1 && fechaIndex != -1 && completadaIndex != -1) {

                val id = cursor.getInt(idIndex)
                val nombre = cursor.getString(nombreIndex)
                val descripcion = cursor.getString(descripcionIndex)
                val fecha = cursor.getString(fechaIndex)
                val completada = cursor.getInt(completadaIndex) == 1

                val tarea = Tarea(id, nombre, descripcion, fecha, completada)
                listaTareas.add(tarea)
            }

        }
        db.close()
        return listaTareas
    }

    fun obtenerTarea(id: Int): Tarea {
        var tarea:Tarea? = null
        val selectQuery = "SELECT  * FROM $TABLE_TAREAS WHERE $KEY_ID_T = $id"
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex(KEY_ID_T)
            val nombreIndex = cursor.getColumnIndex(KEY_NOMBRE_T)
            val descripcionIndex = cursor.getColumnIndex(KEY_DESCRIPCION_T)
            val fechaIndex = cursor.getColumnIndex(KEY_FECHA_T)
            val completadaIndex = cursor.getColumnIndex(KEY_COMPLETADA_T)
            if (idIndex != -1 && nombreIndex != -1 && descripcionIndex != -1 && fechaIndex != -1 && completadaIndex != -1) {

                val id = cursor.getInt(idIndex)
                val nombre = cursor.getString(nombreIndex)
                val descripcion = cursor.getString(descripcionIndex)
                val fecha = cursor.getString(fechaIndex)
                val completada = cursor.getInt(completadaIndex) == 1

                 tarea = Tarea(id, nombre, descripcion, fecha, completada)

            }

        }
        db.close()
        return tarea!!
    }

    fun insertarTarea(tarea : Tarea) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NOMBRE_T, tarea.nombre)
            put(KEY_DESCRIPCION_T, tarea.descripcion)
            put(KEY_FECHA_T, tarea.fecha)
            put(KEY_COMPLETADA_T, if(tarea.completada) 1 else 0)
        }
        db.insert(TABLE_TAREAS, null, values)
        db.close()
    }
    fun eliminarTarea(id: Int) {
        val db = dbHelper.writableDatabase
        db.delete(TABLE_TAREAS, "$KEY_ID_T = $id", null)
        db.close()
    }

    fun actualizarTarea(
        id: Int,
        nombre: String, descripcion: String, fecha: String, checked: Boolean
    ) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NOMBRE_T, nombre)
            put(KEY_DESCRIPCION_T, descripcion)
            put(KEY_FECHA_T, fecha)
            put(KEY_COMPLETADA_T, checked)
        }
        db.update(TABLE_TAREAS, values, "$KEY_ID_T = $id", null)
        db.close()
    }
}