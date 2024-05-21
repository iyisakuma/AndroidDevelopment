package br.igor.eletriccarapp.Data

import br.igor.eletriccarapp.domain.Carro

object CarFactory {
    val carros = listOf(
        Carro(
            id = "1",
            preco = "3",
            bateria = "300 kwh",
            potencia = "200cv",
            recarga = "30min",
            urlPhoto = ""
        ),
        Carro(
            id = "2",
            preco = "4",
            bateria = "500 kwh",
            potencia = "500cv",
            recarga = "30min",
            urlPhoto = ""
        )
    )
}