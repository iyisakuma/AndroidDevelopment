package br.igor.eletriccarapp.presentation

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.igor.eletriccarapp.R
import br.igor.eletriccarapp.domain.Carro
import br.igor.eletriccarapp.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment() {

    lateinit var rv_lista_carro: RecyclerView
    lateinit var fab_calculator: FloatingActionButton
    lateinit var pb_loading : ProgressBar
    lateinit var tv_no_wifi: TextView
    lateinit var iv_empty_state : ImageView
    lateinit var carsApi : CarApi

    var carrosArray : ArrayList<Carro> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        load(view)
        addListeners()
        if(checkInternetStatus(context)){
            //callService()
            getAllCars()
        }else{
            emptyState()
        }

    }

    override fun onResume() {
        super.onResume()
        if(checkInternetStatus(context)){
            callService()
        }else{
            emptyState()
        }
    }
    fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        carsApi = retrofit.create(CarApi::class.java)
    }
    private fun getAllCars(){
        carsApi.getAllCars().enqueue(object : Callback<List<Carro>> {
            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                if (response.isSuccessful) {
                    pb_loading.isVisible = false
                    tv_no_wifi.isVisible = false
                    iv_empty_state.isVisible = false
                    response.body()?.let {
                        setupList(it)
                    }
                } else {
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun emptyState(){
        tv_no_wifi.isVisible = true
        iv_empty_state.isVisible = true
        rv_lista_carro.isVisible = false
    }
    private fun checkInternetStatus(context: Context?) : Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network)?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }else{
            val networkInfo = connectivityManager.activeNetworkInfo?: return false
            return networkInfo.isConnected
        }
    }

    private fun load(view: View) {
        view.apply {
            rv_lista_carro = findViewById(R.id.rv_lista_carro)
            fab_calculator = findViewById(R.id.fab_calculator)
            pb_loading = findViewById(R.id.pb_loading)
            iv_empty_state = findViewById(R.id.iv_empty_state)
            tv_no_wifi = findViewById(R.id.tv_no_wifi)
        }
    }

    private fun setupList(carros: List<Carro>) {
        rv_lista_carro.adapter = CarAdapter(carrosArray)
    }

    private fun addListeners() {
        fab_calculator.setOnClickListener {
            startActivity(Intent(context, CalcularAutonomiaActivity::class.java))
        }
    }

    private fun callService(){
        MyTask().execute("https://igorbag.github.io/cars-api/cars.json")
        pb_loading.visibility = View.GONE
        rv_lista_carro.visibility = View.VISIBLE
    }

    inner class MyTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            pb_loading.visibility = View.VISIBLE
            tv_no_wifi.isVisible = false
            iv_empty_state.isVisible = false
            super.onPreExecute()
            Log.d("MyTask", "Iniciando...")
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray
                var carro: Carro
                for (i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    carro = Carro(
                        id = jsonArray.getJSONObject(i).getString("id"),
                        preco = jsonArray.getJSONObject(i).getString("id"),
                        bateria = jsonArray.getJSONObject(i).getString("id"),
                        potencia = jsonArray.getJSONObject(i).getString("id"),
                        recarga = jsonArray.getJSONObject(i).getString("id"),
                        urlPhoto = jsonArray.getJSONObject(i).getString("id")
                    )
                    carrosArray.add(carro)
                }
            } catch (ex: Exception) {

            }
        }

        override fun doInBackground(vararg params: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                val urlBase = URL(params[0])
                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 6000
                urlConnection.readTimeout = 6000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )
                val responseCode = urlConnection.responseCode
                if(responseCode == HttpURLConnection.HTTP_OK) {
                    var response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)
                }else{
                    Log.e("Erro", "Serviço Indisponível")
                }
            } catch (ex: Exception) {
                Log.e("Erro", "Erro ao realizar processamento....")
            } finally {
                urlConnection?.disconnect()
            }
            return ""
        }
    }
}
