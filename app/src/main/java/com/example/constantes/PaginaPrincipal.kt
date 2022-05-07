package com.example.constantes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

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

        val intent = getIntent()
        val valorPulso = intent.extras?.getInt("valorPulso")
        pulso.text = valorPulso.toString()+" pulsaciones/minuto"

    }
}