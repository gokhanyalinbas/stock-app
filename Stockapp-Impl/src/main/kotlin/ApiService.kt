

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    private val apiInterface : ApiInterface

    companion object {
        var BaseURl = "http://localhost:8080/"
    }

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BaseURl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    suspend fun getStocksById(int id) : Stock {
        return apiInterface.getStocksById(id)
    }
    suspend fun getStocks(int page,int size) : List<Stock> {
        return apiInterface.getStocks(page,size)
    }
    suspend fun createStocks(Stock stock) : Stock {
        return apiInterface.createStocks(stock)
    }
    suspend fun updateStocks(Stock stock) : Stock {
        return apiInterface.updateStocks(stock)
    }
    suspend fun deleteStocksById(int id) : Stock {
        return apiInterface.deleteStocksById(id)
    }


}

