package com.example.cryptomarket.model

import ir.dunijet.dunipool.apiManager.model.ChartData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {



    @Headers(Api_Key)
    @GET("v2/news/")
    fun getTopNews(

       @Query("sortOrder") sortOrder:String="popular"


    ):Call<NewsDataclass>


    @Headers(Api_Key)
    @GET("top/totalvolfull")
    fun getCoinInformation(
        @Query("limit")limit:Int=10,
        @Query("tsym")tsym:String="USD"
    ):Call<CoinsInformation>







@Headers(Api_Key)
@GET("{period}")
fun getChartData(

    @Path("period")period:String,
    @Query("fsym")fromsymbol:String,
    @Query("tsym")tosymbol:String="USD",
@Query("agregate")aggregate:Int,
    @Query("limit")limit:Int












):Call<ChartData>










}