package com.example.cryptomarket.MarketActivity


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptomarket.CoinAboutDataClass
import com.example.cryptomarket.CoinAboutItem
import com.example.cryptomarket.databinding.ActivityMarketBinding
import com.example.cryptomarket.features.COinMarketsActivity
import com.example.cryptomarket.model.ApiManager
import com.example.cryptomarket.model.CoinsInformation
import com.google.gson.Gson

class MArketActivity : AppCompatActivity(),AdapterMarket.CallBackOnCoinsItemClicked {
    lateinit var binding: ActivityMarketBinding
    val apimanager = ApiManager()
    lateinit var catchdata: ArrayList<Pair<String, String>>
    lateinit var aboutDataMap: MutableMap<String, CoinAboutItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMarketBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




        binding.layoutWatchlist.btnMore.setOnClickListener {

            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("https://www.livecoinwatch.com/"))
            startActivity(intent)
        }

binding.swiperefresh.setOnRefreshListener {
marketinformation()
    Handler(Looper.getMainLooper()).postDelayed({


        binding.swiperefresh.isRefreshing=false





    },2000)




}

        getAboutDataFromAssets()
    }


    override fun onResume() {
        super.onResume()
        marketinformation()
    }












    private fun marketinformation() {
        getNewsFromApi()
        getCoinsListFromApi()



    }























    private fun getAboutDataFromAssets() {

        val fileinString=applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }



         aboutDataMap= mutableMapOf<String,CoinAboutItem>()



        val json=Gson()
     val dataAboutAll=   json.fromJson(fileinString,CoinAboutDataClass::class.java)

dataAboutAll.forEach {

    aboutDataMap[it.currencyName]=
        CoinAboutItem(it.info.web,it.info.github,it.info.twt,it.info.desc,it.info.reddit)






}


    }























    private fun getCoinsListFromApi() {


      apimanager.getCoinsList(object :ApiManager.CallbackApi<List<CoinsInformation.Data>>{
          override fun oNSuccess(data: List<CoinsInformation.Data>) {

             showDatainRecycler(data)









          }

          override fun oNError(Error: String) {

              Toast.makeText(this@MArketActivity, Error, Toast.LENGTH_SHORT).show()



          }

      })









    }


    private fun showDatainRecycler(data:List<CoinsInformation.Data>){


val adaptermarket=AdapterMarket(ArrayList(data),this)

        binding.layoutWatchlist.recyclerView.adapter=adaptermarket
binding.layoutWatchlist.recyclerView.layoutManager=LinearLayoutManager(this)






    }

    private fun getNewsFromApi() {

        apimanager.getNews(object : ApiManager.CallbackApi<ArrayList<Pair<String, String>>> {
            override fun oNSuccess(data: ArrayList<Pair<String, String>>) {
                catchdata = data
                refreshNews()
            }

            override fun oNError(Error: String) {
                Toast.makeText(this@MArketActivity, Error, Toast.LENGTH_SHORT).show()
            }


        })
    }

    private fun refreshNews() {

        val random = (0..49).random()

        binding.layoutNews.txtNews.text = catchdata[random].first
        binding.layoutNews.txtNews.setOnClickListener {

            refreshNews()
        }
        binding.layoutNews.imgnews.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(catchdata[random].second))

            startActivity(intent)

        }


    }

    override fun sendData(oneOfCoinsItem: CoinsInformation.Data) {
        val intent=Intent(this,COinMarketsActivity::class.java)

        intent.putExtra("sendExtra",oneOfCoinsItem)

        startActivity(intent)





        intent.putExtra("aboutdatasend",aboutDataMap[oneOfCoinsItem.coinInfo.name])

startActivity(intent)



    }


}


