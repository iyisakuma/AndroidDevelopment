package br.igor.eletriccarapp.domain

data class Carro(
    val id: String,
    val preco: String,
    val bateria: String,
    val potencia: String,
    val recarga: String,
    val urlPhoto: String

) {
    override fun toString(): String {
        return "Carro(id='$id', preco='$preco', bateria='$bateria', potencia='$potencia', recarga='$recarga', urlPhoto='$urlPhoto')"
    }
}

