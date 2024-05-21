package br.igor.eletriccarapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.igor.eletriccarapp.R

class CalcularAutonomiaActivity : AppCompatActivity() {

    lateinit var et_km_percorrido : EditText
    lateinit var et_khw : EditText
    lateinit var bt_calcular : Button
    lateinit var tv_resultado : TextView
    lateinit var iv_return : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_autonomia)
        load()
        addListeners()
    }

    private fun load(){
        et_khw = findViewById(R.id.et_khw)
        et_km_percorrido = findViewById(R.id.et_km_percorrido)
        bt_calcular = findViewById(R.id.bt_calcular)
        tv_resultado = findViewById(R.id.tv_resultado)
        iv_return = findViewById(R.id.iv_return)
    }
    private fun addListeners(){
        bt_calcular.setOnClickListener {
            calculaConsumo()
        }
        iv_return.setOnClickListener{
            finish()
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