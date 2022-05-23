package com.example.constantes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PaginaPrincipal : AppCompatActivity() {

    private lateinit var buttonPulso: Button
    private lateinit var buttonTension:Button
    private lateinit var buttonTemperatura:Button
    private lateinit var buttonPasos:Button
    private lateinit var buttonVer:Button
    private lateinit var buttonVerMenos:Button
    private lateinit var pregunta1:TextView
    private lateinit var pregunta2:TextView
    private lateinit var respuesta1: TextView
    private lateinit var respuesta2: TextView
    private lateinit var pulso : TextView
    private lateinit var pasos :  TextView
    private lateinit var idUsuario : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina_principal)

        buttonPulso = findViewById(R.id.buttonPulso)
        buttonTension=findViewById(R.id.buttonTension)
        buttonTemperatura=findViewById(R.id.buttonTemperatura)
        buttonPasos=findViewById(R.id.buttonPasos)
        buttonVer=findViewById(R.id.buttonVer)
        buttonVerMenos=findViewById(R.id.buttonVerMenos)
        pregunta1=findViewById(R.id.pregunta1)
        pregunta2= findViewById(R.id.pregunta2)
        respuesta1=findViewById(R.id.respuesta1)
        respuesta2 = findViewById(R.id.respuesta2)
        pulso = findViewById(R.id.pulsoText)

        val emailUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        idUsuario = conseguirID(emailUser)


        buttonVer.setOnClickListener {
            buttonTemperatura.visibility= View.VISIBLE
            buttonTension.visibility=View.VISIBLE
            buttonVer.visibility=View.GONE
            buttonVerMenos.visibility=View.VISIBLE
        }
        buttonVerMenos.setOnClickListener {
            buttonTemperatura.visibility= View.GONE
            buttonTension.visibility=View.GONE
            buttonVer.visibility=View.VISIBLE
            buttonVerMenos.visibility=View.INVISIBLE
        }

        pregunta1.setOnClickListener {
            respuesta1.visibility = View.VISIBLE
            respuesta2.visibility = View.GONE
        }
        pregunta2.setOnClickListener {
            respuesta2.visibility = View.VISIBLE
            respuesta1.visibility = View.GONE
        }

        buttonPulso.setOnClickListener {
            val intent = Intent(this, PulsoActivity::class.java).apply{
                startActivity(this)
            }
        }

    mostrarPulso()

    }

    private fun conseguirID(email : String) : String{
        var identificador = ""
        val myDB = FirebaseFirestore.getInstance()
        val colUsuarios = myDB.collection("usuarios")
        val task: Task<QuerySnapshot> = colUsuarios.get()
        while (!task.isComplete) {
        }
        task.result?.forEach{u->
            if(u.get("email") == email){
                identificador = u.id.toString()
            }

        }
        return identificador
    }


    private fun pasardeStringaDate(fecha : String) : Date{

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = simpleDateFormat.parse(fecha)

        return date

    }
    public fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun pulsoMasReciente() : String {
        val myDB = FirebaseFirestore.getInstance()
        var idPulso = ""
        var resFecha = pasardeStringaDate("10/01/2020 00:00:00")


        val coleccion =
            myDB.collection("usuarios").document(idUsuario).collection("constantes").document("pulso").collection("pulsos recogidos")
        val task: Task<QuerySnapshot> = coleccion.get()
        while (!task.isComplete) {

        }


        task.result?.forEach { doc ->
            val fechaDoc = pasardeStringaDate(doc.get("fecha").toString())
            if (fechaDoc.after(resFecha)) {
                resFecha = fechaDoc
                idPulso = doc.id
            }


            }
        return idPulso

        }




    private fun mostrarPulso(){
        val myDB = FirebaseFirestore.getInstance()
        val pulsoReciente = pulsoMasReciente()
        if (!pulsoReciente.isNullOrEmpty()){
            val valorPulso = myDB.collection("usuarios").document(idUsuario).collection("constantes").document("pulso").collection("pulsos recogidos")
                .document(pulsoReciente).get().addOnSuccessListener {
                    pulso.setText(it.getString("valor") + " latidos/minuto")
                }
        }
        else{
            pulso.setText("No hay pulsos aún")
        }




        }
}