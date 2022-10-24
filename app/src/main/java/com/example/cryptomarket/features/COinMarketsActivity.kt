package com.example.cryptomarket.features

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.cryptomarket.CoinAboutItem
import com.example.cryptomarket.R
import com.example.cryptomarket.databinding.ActivityCoinMarketsBinding
import com.example.cryptomarket.model.*
import ir.dunijet.dunipool.apiManager.model.ChartData

class COinMarketsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoinMarketsBinding
    lateinit var reciveddata: CoinsInformation.Data
    lateinit var recivedAbout: CoinAboutItem

    val apimanager = ApiManager()
    override fun onCreate(savedInstanceState: Bundle?) {


        binding = ActivityCoinMarketsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




        reciveddata = intent.getParcelableExtra<CoinsInformation.Data>("sendExtra")!!


if (intent.getParcelableExtra<CoinAboutItem>("aboutdatasend")!=null){

    recivedAbout = intent.getParcelableExtra<CoinAboutItem>("aboutdatasend")!!

}else{

    recivedAbout= CoinAboutItem()


}


        binding.layouttoolbarcoin.toolbarmain.title = reciveddata.coinInfo.fullName




        coinUiInformation()


    }














    private fun coinUiInformation() {


        staticInformation()

        aBoutInformation()


        chartMiddleInformation()


    }



















    @SuppressLint("SetTextI18n")
    private fun chartMiddleInformation() {
        var period: String = HOUR

       binding.chartbottom.radio12h.isChecked=true
        requestAndShowchart(period)

binding.pricecharttop.txtprice.text=reciveddata.dISPLAY.uSD.pRICE

        binding.pricecharttop.textdolar.text=reciveddata.dISPLAY.uSD.cHANGE24HOUR





        val taghir=reciveddata.rAW.uSD.cHANGEPCT24HOUR
        if (taghir>0){

            binding.pricecharttop.textdarsad.setTextColor(ContextCompat.getColor(binding.root.context,R.color.colorGain))




            binding.pricecharttop.textFlesh.setTextColor(ContextCompat.getColor(binding.root.context,R.color.colorGain) )
            binding.pricecharttop.textFlesh.text="▲"



            binding.sparkchartmiddle.sparkveiwmain.lineColor=ContextCompat.getColor(binding.root.context,R.color.colorGain)



            if (reciveddata.coinInfo.name=="BUSD"){

                binding.pricecharttop.textdarsad.text="0%"

            }else{

                binding.pricecharttop.textdarsad.text=reciveddata.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0,5)+"%"
            }





        }else if (taghir<0){

            binding.pricecharttop.textdarsad.setTextColor(ContextCompat.getColor(binding.root.context,R.color.colorLoss))




            binding.pricecharttop.textFlesh.setTextColor(ContextCompat.getColor(binding.root.context,R.color.colorLoss) )

            binding.pricecharttop.textFlesh.text="▼"

            binding.sparkchartmiddle.sparkveiwmain.lineColor=ContextCompat.getColor(binding.root.context,R.color.colorLoss)

        }





        binding.chartbottom.radiogroupchart.setOnCheckedChangeListener { _, checkedId ->


            when (checkedId) {


                R.id.radio_12h -> {
                    period = HOUR
                }
                R.id.radio_1D -> {
                    period = HOURS24
                }
                R.id.radio_1W -> {
                    period = WEEK
                }
                R.id.radio_1M -> {
                    period = MONTH
                }
                R.id.radio_3M -> {
                    period = MONTH3
                }
                R.id.radio_1Y -> {
                    period = YEAR
                }
                R.id.radio_all -> {
                    period = ALL
                }

            }
            requestAndShowchart(period)

        }


        binding.sparkchartmiddle.sparkveiwmain.setScrubListener {

            if (it == null) {


                binding.pricecharttop.txtprice.text = reciveddata.dISPLAY.uSD.pRICE


            } else {

                binding.pricecharttop.txtprice.text = "%" + (it as ChartData.Data).close.toString()


            }
        }


    }
    fun requestAndShowchart(period:String){


            apimanager.getChart(
                reciveddata.coinInfo.name,
                period,
                object : ApiManager.CallbackApi<Pair<List<ChartData.Data>, ChartData.Data?>> {
                    override fun oNSuccess(data: Pair<List<ChartData.Data>, ChartData.Data?>) {

                        val chartadapter = ChartAdapter(data.first, data.second?.open.toString())


                        binding.sparkchartmiddle.sparkveiwmain.adapter = chartadapter


                    }

                    override fun oNError(Error: String) {
                        Toast.makeText(this@COinMarketsActivity, Error, Toast.LENGTH_SHORT).show()
                    }


                })






        }






















    private fun aBoutInformation() {

        binding.layoutabout.txtna.text = recivedAbout.coinWeb
        binding.layoutabout.txtnagithub.text = recivedAbout.coinGithub
        binding.layoutabout.txtnareddit.text = recivedAbout.coinReddit
        binding.layoutabout.txtnatwitter.text = "@" + recivedAbout.coinTwitter


        binding.layoutabout.somemoredata.text = recivedAbout.coinDecription

        binding.layoutabout.txtna.setOnClickListener {


            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recivedAbout.coinWeb))
            startActivity(intent)
        }

        binding.layoutabout.txtnagithub.setOnClickListener {


            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recivedAbout.coinGithub))
            startActivity(intent)

        }


        binding.layoutabout.txtnareddit.setOnClickListener {


            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recivedAbout.coinReddit))
            startActivity(intent)

        }

        binding.layoutabout.txtnatwitter.setOnClickListener {


            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(Base_UrlTwitter + recivedAbout.coinTwitter))
            startActivity(intent)

        }


    }























    private fun staticInformation() {


        binding.layoutstatic.dollarOpenStatic.text = reciveddata.dISPLAY.uSD.oPEN24HOUR

        binding.layoutstatic.dollarTodayStatic.text = reciveddata.dISPLAY.uSD.hIGH24HOUR
        binding.layoutstatic.dolarTodaylowStatic.text = reciveddata.dISPLAY.uSD.lOW24HOUR

        binding.layoutstatic.dolarchangetodayStatic.text = reciveddata.dISPLAY.uSD.cHANGE24HOUR


        binding.layoutstatic.monthVolume.text = reciveddata.coinInfo.algorithm
        binding.layoutstatic.monthAvg.text = reciveddata.dISPLAY.uSD.tOTALVOLUME24H

        binding.layoutstatic.dollarMarketcap.text = reciveddata.dISPLAY.uSD.mKTCAP


        binding.layoutstatic.dollarSupplay.text = reciveddata.dISPLAY.uSD.sUPPLY


    }

}