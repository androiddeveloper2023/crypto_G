package com.example.cryptomarket.model

import ir.dunijet.dunipool.apiManager.model.ChartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {
val apiservice:ApiService
   init {
       val retrofit=Retrofit
           .Builder()
           .baseUrl(Base_Url)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
 apiservice=retrofit.create(ApiService::class.java)

   }


fun getNews(callbackApi:CallbackApi<ArrayList<Pair<String,String>>>){


    apiservice.getTopNews().enqueue(object:Callback<NewsDataclass>{
        override fun onResponse(call: Call<NewsDataclass>, response: Response<NewsDataclass>) {
          val news=response.body()!!
            val sendingNews:(ArrayList<Pair<String,String>>)= arrayListOf()
            news.data.forEach {



                sendingNews.add(Pair(it.title,it.url))



            }
            callbackApi.oNSuccess(sendingNews)
        }

        override fun onFailure(call: Call<NewsDataclass>, t: Throwable) {
            callbackApi.oNError(t.message!!)
        }


    })










}



























    fun getCoinsList(callbacApi:CallbackApi<List<CoinsInformation.Data>>){


     apiservice.getCoinInformation().enqueue(object:Callback<CoinsInformation>{
         override fun onResponse(call: Call<CoinsInformation>, response: Response<CoinsInformation>) {
             val sendCoins=response.body()!!

             callbacApi.oNSuccess(sendCoins.data)


         }

         override fun onFailure(call: Call<CoinsInformation>, t: Throwable) {

             callbacApi.oNError(t.message!!)


         }


     })












    }



fun getChart(symbol:String,period:String,callbackapi:CallbackApi<Pair<List<ChartData.Data>,ChartData.Data?>>){



 var histoperiod=""
    var limit=30
    var aggregate=1


    when(period){

      HOUR->{

          histoperiod= HISTO_MINUTE
          limit=60
          aggregate=12


      }



        HOURS24->{

            histoperiod= HISTO_HOUR
            limit=24







        }



        WEEK->{


            histoperiod= HISTO_HOUR
            aggregate=6









        }
        MONTH->{

            histoperiod= HISTO_DAY
            limit=30






        }
        MONTH3->{

            histoperiod= HISTO_DAY
            limit=90





        }


        YEAR->{


            histoperiod= HISTO_DAY
            aggregate=13



        }


        ALL->{

            histoperiod= HISTO_DAY
            aggregate=30
            limit=2000



        }





    }









    apiservice.getChartData(histoperiod,symbol,tosymbol = "USD",aggregate,limit).enqueue(object :Callback<ChartData>{
        override fun onResponse(call: Call<ChartData>, response: Response<ChartData>) {



            val fullData=response.body()!!
            val data1=fullData.data
            val data2=fullData.data.maxByOrNull {

                it.close.toFloat()


            }

            val returningData=Pair(data1,data2)



callbackapi.oNSuccess(returningData)








        }

        override fun onFailure(call: Call<ChartData>, t: Throwable) {

callbackapi.oNError(t.message!!)





        }


    })







}









    interface CallbackApi<T>{

        fun oNSuccess(data:T)

        fun oNError(Error:String)




    }



}