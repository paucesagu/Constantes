package com.example.constantes

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class PulsoActivity : AppCompatActivity() {

    private lateinit var pulso: EditText
    private lateinit var hora: ImageButton
    private lateinit var guardar:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulso)

        pulso = findViewById(R.id.editTextPulso)
        hora = findViewById(R.id.buttonHora)
        guardar= findViewById(R.id.savebutton)


    }
    public fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }



    public fun onClickGuardar(view : View){
        val myDB = FirebaseFirestore.getInstance()
        val fechaActual = Calendar.getInstance().time
        val fechaEnString = fechaActual.toString("dd/MM/yyyy HH:mm:ss")


        val valorPulso = pulso.text.toString()
        val rangoPulso = 15..350



        if(!valorPulso.isNullOrBlank() and (valorPulso.toInt() in rangoPulso)) {
            myDB.collection("constantes").document("pulso").collection("pulsos recogidos").add(mapOf(
                "valor" to valorPulso,
                "fecha" to fechaEnString))
            showAlert("Pulso guardado correctamente")
            val intent = Intent(this, PaginaPrincipal::class.java).apply{
                startActivity(this)
            }
            }

        else{
            showAlert("El valor introducido está fuera de rango, debe estar entre 15 y 350")
        }
    }

    private fun showAlert(text: String){
        //mostramos cuadro de dialogo
        //inicializar cuadro de dialogo con la clase alertDialog
        val builder = AlertDialog.Builder(this)
        builder.setMessage(text)
        builder.setPositiveButton("Aceptar", null) //ponemos null porque lo qe queremos es que se quite no que
        //nos muestre una nueva pantalla
        //mostrar dialogo
        builder.show()

    }

}


