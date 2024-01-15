package com.example.examen2ev.base

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Bloque companion object para definir constantes que serán usadas en toda la clase.
    // Son como los valores estáticos en Java
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

    override fun onCreate(db: SQLiteDatabase) {
        val createTareasTable = ("CREATE TABLE " + TABLE_TAREAS + " ( "
                + "$KEY_ID_T INTEGER PRIMARY KEY, $KEY_NOMBRE_T TEXT, $KEY_DESCRIPCION_T TEXT, $KEY_FECHA_T TEXT, $KEY_COMPLETADA_T INTEGER "
                + ")")


        db.execSQL(createTareasTable)
    }

    // Método llamado cuando se necesita actualizar la base de datos, por ejemplo, cuando se incrementa DATABASE_VERSION.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Elimina la tabla existente y crea una nueva.
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TAREAS")

        onCreate(db)
    }

    // Método para obtener todos los discos de la base de datos.

}