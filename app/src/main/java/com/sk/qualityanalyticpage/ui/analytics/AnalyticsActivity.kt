package com.sk.qualityanalyticpage.ui.analytics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.qualityanalyticpage.adapter.AnalyticAdapter
import com.sk.qualityanalyticpage.adapter.LinearItemDecorator
import com.sk.qualityanalyticpage.adapter.OnItemClickListener
import com.sk.qualityanalyticpage.databinding.AnalyticsActivityMainBinding
import com.sk.qualityanalyticpage.ui.analyticdetails.AnalyticDetailsPage
import com.sk.qualityanalyticpage.utilites.Constant
import com.sk.qualityanalyticpage.utilites.Injector

class AnalyticsActivity : AppCompatActivity(), OnItemClickListener {

    private val TAG = AppCompatActivity::class.java.simpleName
    private lateinit var binding: AnalyticsActivityMainBinding
    private lateinit var mAdapter: AnalyticAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AnalyticsActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initUserInterface()
    }

    private fun initUserInterface() {
        val factory = Injector.provideAnalyticsViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory)
            .get(AnalyticsViewModel::class.java)
        mAdapter = AnalyticAdapter(this, this)
        binding.dataListRv.layoutManager = LinearLayoutManager(this)
        binding.dataListRv.addItemDecoration(LinearItemDecorator(10, 10))
        binding.dataListRv.adapter = mAdapter
        lifecycle.addObserver(viewModel)
        viewModel.getConnectionStatus().observe(this, Observer {
            when (it) {
                0 -> {
                    Log.d(TAG, "Connected..")
                }
                else -> {
                    Log.d(TAG, "DisConnected..")
                }
            }
        })
        viewModel.getAnalyticsDetails().observe(this, Observer {
            mAdapter.updateDataList(it)
        })
    }

    override fun openGraph(name: String?) {
        val intent = Intent(this@AnalyticsActivity, AnalyticDetailsPage::class.java)
        intent.putExtra(Constant.messageKey, name)
        startActivity(intent)
    }
}
