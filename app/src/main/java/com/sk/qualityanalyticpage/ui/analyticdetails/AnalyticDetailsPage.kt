package com.sk.qualityanalyticpage.ui.analyticdetails

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.sk.qualityanalyticpage.databinding.ActivityAnalyticDetailsPageBinding
import com.sk.qualityanalyticpage.ui.analytics.AnalyticsViewModel
import com.sk.qualityanalyticpage.utilites.CharValueFormater
import com.sk.qualityanalyticpage.utilites.Constant
import com.sk.qualityanalyticpage.utilites.Injector

class AnalyticDetailsPage : AppCompatActivity() {
    private lateinit var binding : ActivityAnalyticDetailsPageBinding
    private val TAG = AnalyticDetailsPage::class.java.simpleName
    lateinit var key : String
    private val yValueList = ArrayList<Entry>()
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyticDetailsPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        extractKey()
        setUserInterface()
    }

    private fun setUserInterface() {
        val factory = Injector.provideAnalyticsViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory)
                .get(AnalyticsViewModel::class.java)
        lifecycle.addObserver(viewModel)
        binding.chart.description.isEnabled = false
        binding.chart.legend.isEnabled = false
        binding.chart.setPinchZoom(true)
        binding.chart.xAxis.enableGridDashedLine(5f, 5f, 0f)
        binding.chart.axisRight.enableGridDashedLine(5f, 0f, 0f)
        binding.chart.axisLeft.enableGridDashedLine(5f, 5f, 0f)

        binding.chart.xAxis.labelCount = 10
        binding.chart.xAxis.granularity = 1f
        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chart.xAxis.valueFormatter = CharValueFormater()
        binding.chart.axisRight.isEnabled = false
        binding.chart.axisLeft.granularity = 1f
        binding.chart.axisLeft.mAxisMaximum = 500f
        binding.chart.axisLeft.labelCount = 10
        binding.chart.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        binding.chart.setVisibleXRangeMaximum(10f);

        viewModel.getConnectionStatus().observe(this, Observer {
            when(it) {
                0 -> {
                    Log.d(TAG, "Connected..")}
                else -> {
                    Log.d(TAG, "DisConnected..")}
            }
        })
        viewModel.getAnalyticsDetails().observe(this, Observer {
           for (item in it) {
               if (item.city.equals(key, true)) {
                   val value = item.value
                   yValueList.add(Entry(count.toFloat(), value!!.toFloat(), "0"))
                   count++
                   synchronized(this) {
                       updateChange()
                   }
               }
           }
        })
    }

    private fun extractKey() {
        key = intent.getStringExtra(Constant.messageKey)!!
        title = key
    }

    private fun updateChange() {
        val set1 = LineDataSet(yValueList, "DataSet 1")
        set1.color = Color.BLUE
        set1.setCircleColor(Color.BLUE)
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 0f
        set1.setDrawFilled(false)
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        val data = LineData(dataSets)
        binding.chart.data = data
        binding.chart.notifyDataSetChanged()
        binding.chart.invalidate()
    }
}