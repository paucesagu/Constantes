package com.example.constantes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PaginaInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagina_inicio)
        val tiempo = 2000L
        val intent = Intent(this, PaginaPrincipal::class.java)
        val handler = Handler()
        handler.postDelayed({
            startActivity(intent)
        }, tiempo)



    }
}