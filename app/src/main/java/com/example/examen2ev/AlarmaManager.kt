package com.example.examen2ev

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Calendar

class AlarmaManager (val context: Context, val fecha:String, val hora:String, val descripcion:String){

     fun establecerAlarma() {
        // Obtengo una instancia del calendario y le fijo la hora y minutos obtenidos por parámetro
        val calendarioAlarma = Calendar.getInstance().apply {
            set(Calendar.YEAR, fecha.substring(0,4).toInt())
            set(Calendar.MONTH, fecha.substring(5,7).toInt()-1)
            set(Calendar.HOUR_OF_DAY, hora.split(":")[0].toInt())
            set(Calendar.MINUTE, hora.split(":")[1].toInt())
            set(Calendar.SECOND, 0)
        }

        // Obtengo una instancia del servicio ALARM_SERVICE de tipo AlarmManager
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Creo un intent que paso a la clase AlarmReceiver que hereda de BroadcastReceiver
        val intent = Intent(context, AlarmaReceiver::class.java)
        // Declaro un pendingIntent, que es un tipo de intent que queda pendiente de que alguien
        // lo ejecute, será el AlarmManager quien lo ejecute
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        Log.d("AlarmaFragment", "Alarma creada ${calendarioAlarma.time}")
        Log.d(
            "AlarmaFragment",
            "Alarma creada ${calendarioAlarma[Calendar.HOUR_OF_DAY]}:${calendarioAlarma[Calendar.MINUTE]}"
        )
        // Configuramos la alarma en el tiempo especificado y ejecutará el pendintIntent
        // que en este caso crea la notificación
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarioAlarma.timeInMillis, pendingIntent)
    }
}