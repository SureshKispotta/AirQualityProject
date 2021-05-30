package com.sk.qualityanalyticpage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sk.qualityanalyticpage.R
import com.sk.qualityanalyticpage.data.AnalyticsDat
import com.sk.qualityanalyticpage.databinding.AnalyticItemDetailsBinding
import com.sk.qualityanalyticpage.utilites.Constant
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class AnalyticAdapter(private val context: Context,private  val listener : OnItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val mDataList: ArrayList<AnalyticsDat> = ArrayList()
    private val valueFormater = DecimalFormat("#.##").apply {
        this.roundingMode = RoundingMode.CEILING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.analytic_item_details, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.binding.cityTextView.text = mDataList[position].city
                holder.binding.aiqValueTextView.text = valueFormater.format(mDataList[position].value)
                holder.binding.aiqValueTextView.setTextColor(Constant
                        .getColorCode(context, mDataList[position].value?.roundToInt()))
                holder.updateTime(mDataList[position].updateTime)
                holder.binding.root.setOnClickListener(View.OnClickListener {
                    listener.openGraph(mDataList[position].city)
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun updateDataList(dataList: List<AnalyticsDat>) {
        synchronized(this) {
            dataList.forEach() {
                when(val position = mDataList.indexOf(it)) {
                    -1 -> {
                        mDataList.add(it)
                    }
                    else -> {
                        mDataList[position] = it
                    }
                }
                notifyDataSetChanged()
            }
        }
    }

    private class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = AnalyticItemDetailsBinding.bind(view)
        val showingFormat = SimpleDateFormat("hh:mm aa",  Locale.ENGLISH)
        fun updateTime(updateDate: Date) {
            val diff = Date().time - updateDate.time
            val hour = (diff / (1000 * 60 * 60)).toInt()
            val mins = ((diff/(1000*60)) % 60).toInt()
            binding.statusTextView.text = when(hour) {
                0 -> {
                    when(mins) {
                        0 -> {"A few seconds ago"}
                        in 1..2 -> {"A minutes ago"}
                        else -> {showingFormat.format(updateDate)}
                    }
                }
                else -> {showingFormat.format(updateDate)}
            }
        }
    }

}