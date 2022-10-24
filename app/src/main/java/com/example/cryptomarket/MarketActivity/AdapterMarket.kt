package com.example.cryptomarket.MarketActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptomarket.R
import com.example.cryptomarket.databinding.ItemRecyclerBinding
import com.example.cryptomarket.model.Base_Image_Url
import com.example.cryptomarket.model.CoinsInformation

class AdapterMarket(private val data:ArrayList<CoinsInformation.Data>,private val callBackOnCoinsItemClicked:CallBackOnCoinsItemClicked):RecyclerView.Adapter<AdapterMarket.MarketHolder>() {

    lateinit var binding: ItemRecyclerBinding

    inner class MarketHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {


        fun onBindViews(oneOfCoinsItem: CoinsInformation.Data) {


            binding.txtBitcoin.text = oneOfCoinsItem.coinInfo.fullName


            val coinbase = oneOfCoinsItem.rAW.uSD.cHANGEPCT24HOUR
            if (coinbase>0)

                {

                    binding.txtCoinbase.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.colorGain
                        )
                    )
                    binding.txtCoinbase.text = oneOfCoinsItem.dISPLAY.uSD.cHANGEPCT24HOUR+"%"





            }else if (coinbase<0){


                binding.txtCoinbase.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorLoss
                    )
                )
                binding.txtCoinbase.text = oneOfCoinsItem.dISPLAY.uSD.cHANGEPCT24HOUR+"%"




            }else{
                binding.txtCoinbase.text ="0%"
            }




                binding.txtprice.text = oneOfCoinsItem.dISPLAY.uSD.pRICE
                binding.txtMillion.text = oneOfCoinsItem.dISPLAY.uSD.mKTCAP

                Glide.with(itemView)
                    .load(Base_Image_Url + oneOfCoinsItem.coinInfo.imageUrl)
                    .into(binding.imgRecycler)




                itemView.setOnClickListener {

                    callBackOnCoinsItemClicked.sendData(oneOfCoinsItem)


                }


            }







    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketHolder {
        binding =
            ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MarketHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MarketHolder, position: Int) {
        holder.onBindViews(data[position])
    }

    override fun getItemCount(): Int=data.size


    interface CallBackOnCoinsItemClicked{


        fun sendData(oneOfCoinsItem: CoinsInformation.Data)
    }

}


