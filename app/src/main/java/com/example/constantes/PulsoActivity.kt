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
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import java.text.SimpleDateFormat
import java.util.*

class PulsoActivity : AppCompatActivity() {

    private lateinit var pulso: EditText

    private lateinit var guardar:Button
    private lateinit var idUsuario : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulso)

        pulso = findViewById(R.id.editTextPulso)

        guardar= findViewById(R.id.savebutton)


        val emailUser = FirebaseAuth.getInstance().currentUser?.email.toString()
        idUsuario = conseguirID(emailUser)

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
            myDB.collection("usuarios").document(idUsuario).collection("constantes").document("pulso").collection("pulsos recogidos").add(mapOf(
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


