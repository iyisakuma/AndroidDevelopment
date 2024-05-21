package br.igor.eletriccarapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.igor.eletriccarapp.R
import br.igor.eletriccarapp.domain.Carro


class CarAdapter(private val carros: List<Carro>) : RecyclerView.Adapter<CarAdapter.ViewHolder> (){
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val preco: TextView
        val bateria: TextView
        val potencia : TextView
        val recarga : TextView
        init{
            view.apply {
                preco = findViewById(R.id.tv_preco_value)
                bateria = findViewById(R.id.tv_bateria_value)
                potencia = findViewById(R.id.tv_potencia_value)
                recarga = findViewById(R.id.tv_recarga_value)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carro_item, parent, false)
        return  ViewHolder(view)
    }

    //
    override fun getItemCount(): Int {
        return carros.size
    }

    //Pega o conteudo de view e troca pela informação de item de uma lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.preco.text = carros[position].preco
        holder.bateria.text = carros[position].bateria
        holder.recarga.text = carros[position].recarga
        holder.potencia.text = carros[position].potencia
    }
}