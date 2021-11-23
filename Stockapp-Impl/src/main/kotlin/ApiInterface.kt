import com.example.retrofitsample.model.Comments
import retrofit2.http.GET

interface ApiInterface {

    @GET("/api/stocks/{id}")
    suspend fun getStocksById(@Path("id") int id) :Stock
    @POST("/api/stocks")
    suspend fun createStocks(@Body Stock stock) :Stock
    @GET("/api/stocks")
    suspend fun getStocks(@Query("page") int page,@Query("page") int size) : List<Stock>
    @PATCH("/api/stocks")
    suspend fun updateStocks(@Body Stock stock) :Stock
    @DELETE("/api/stocks/{id}")
    suspend fun deleteStocksById(@Path("id") int id) :Stock
}