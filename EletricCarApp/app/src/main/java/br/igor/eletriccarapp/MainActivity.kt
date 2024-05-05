package br.igor.eletriccarapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    lateinit var et_km_percorrido : EditText
    lateinit var et_khw : EditText
    lateinit var bt_calcular : Button
    lateinit var tv_resultado : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        load()
        addListeners()
    }

    private fun load(){
        et_khw = findViewById(R.id.et_khw)
        et_km_percorrido = findViewById(R.id.et_km_percorrido)
        bt_calcular = findViewById(R.id.bt_calcular)
        tv_resultado = findViewById(R.id.tv_resultado)
    }

    private fun addListeners(){
        bt_calcular.setOnClickListener {calculaConsumo()
        }
    }

    private fun calculaConsumo(){

        val precoKmW = et_khw.text.toString().toFloat()
        val kmPercorrido = et_km_percorrido.text.toString().toFloat()

        val resultado = kmPercorrido * precoKmW
        tv_resultado.setText("Resultado: RS " + resultado )
        tv_resultado.visibility = View.VISIBLE

    }
}