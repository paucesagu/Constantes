package com.example.constantes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegistarseActivity : AppCompatActivity() {

    private lateinit var correo: EditText
    private lateinit var nombre: EditText
    private lateinit var apellidos: EditText
    private lateinit var fechaNacimiento: EditText
    private lateinit var telefono: EditText
    private lateinit var contraseña: EditText
    private lateinit var contraseña2: EditText
    private lateinit var registrarse: Button
    private lateinit var dni : EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registarse)


        correo = findViewById(R.id.editTextEmail)
        nombre = findViewById(R.id.editTextNombre)
        apellidos = findViewById(R.id.editTextApellidos)
        fechaNacimiento = findViewById(R.id.editTextFecha)
        telefono = findViewById(R.id.editTextTelefono)
        contraseña = findViewById(R.id.editTextContraseña)
        contraseña2 = findViewById(R.id.editTextContraseña2)
        registrarse = findViewById(R.id.buttonRegistrarse2)
        dni = findViewById(R.id.editTextDNI)


    }

    private fun actualizarBDFirestore(dni: String) {


        if (dni.isNotEmpty()) {
            FirebaseFirestore.getInstance().collection("usuarios").document(dni)
                .set(
                    mapOf(
                        "nombre" to nombre.text.toString(),
                        "apellidos" to apellidos.text.toString(),
                        "email" to correo.text.toString(),
                        "telefono" to telefono.text.toString(),
                        "fechaNacimiento" to fechaNacimiento.text.toString()
                    )
                )
        }

    }

    public fun onClickRegistrar(view:View) {
        val e1 =
            Regex("(\\+34|0034|34)?[ -]*(6|7)[ -]*([0-9][ -]*){8}") //para expresiones regulares
        val e1error = !e1.matches(telefono.text.toString()) //comprobacion
        if (e1error) {
            //Error
            Log.e("ERROR", "El teléfono introducido no es correcto")
            telefono.error = "Teléfono incorrecto"
        }
        val e2 = Regex("^[0-9]{8,8}[A-Za-z]\$")
        val e2error = !e2.matches(dni.text.toString())
        if (e2error) {
            //Error
            Log.e("ERROR", "El DNI introducida no es correcta")
            dni.error = "DNI incorrecto"
        }
        if (!e1error && !e2error) {
            //guardar en la base de datos
            guardarDatos()



        }
    }


    private fun guardarDatos() {
        if (correo.text.isNotEmpty() && contraseña.text.isNotEmpty() && (contraseña.text.toString() == contraseña2.text.toString())) {
            //creamos usuario en Firebase
            Firebase.auth.createUserWithEmailAndPassword(
                correo.text.toString(), contraseña.text.toString()
            ).addOnCompleteListener {
                //añadimos un listener para que nos compruebe si se ha registrado correctamente
                if (it.isSuccessful) { /*it es el resultado de las acciones que llama al listener*/
                    actualizarBDFirestore(dni.text.toString())
                    val intent = Intent(this, PaginaPrincipal::class.java).apply {
                        startActivity(this)
                    }
                }
                else{
                        showAlert("Se ha producido un error en el registro")
                    }
                }
            }
            else{

                showAlert("Revisa los campos obligatorios y las contraseñas deben ser iguales")
            }


        }


    private fun showAlert(text: String){
        //mostramos cuadro de dialogo
        //inicializar cuadro de dialogo con la clase alertDialog
        val builder = android.app.AlertDialog.Builder(this)
        builder.setMessage(text)
        builder.setPositiveButton("Aceptar", null) //ponemos null porque lo qe queremos es que se quite no que
        //nos muestre una nueva pantalla
        //mostrar dialogo
        builder.show()

    }

}
