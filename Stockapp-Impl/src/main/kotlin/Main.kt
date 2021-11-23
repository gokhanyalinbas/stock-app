fun main(args: Array<String>) {
   println("Program arguments: ${args.joinToString()}")
    private val apiService : ApiService = ApiService()

    val resp = apiService.getStocks(1,2)
    resp=apiService.getStocksById(1)
    resp=apiService.deleteStocksById(1)


}