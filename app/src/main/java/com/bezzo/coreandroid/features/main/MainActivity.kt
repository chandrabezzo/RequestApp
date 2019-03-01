package com.bezzo.coreandroid.features.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bezzo.core.base.BaseActivity
import com.bezzo.core.data.model.Country
import com.bezzo.core.listener.OnLoadMoreListener
import com.bezzo.coreandroid.R
import com.bezzo.coreandroid.adapter.CountryRVAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContracts.View {

    @Inject
    lateinit var presenter : MainPresenter<MainContracts.View>

    lateinit var adapter : CountryRVAdapter
    val list = ArrayList<Country>()
    lateinit var onLoadMoreListener : OnLoadMoreListener
    val linearLayoutManager = LinearLayoutManager(this)

    override fun onInitializedView(savedInstanceState: Bundle?) {
        presenter.onAttach(this)

        setActionBarTitle(getString(R.string.beranda))
        displayHome()

        adapter = CountryRVAdapter(this, list)

        initRecyclerView()
        presenter.getCountries(20)

        sr_list.setOnRefreshListener {
            presenter.getCountries(20)
        }
    }

    override fun hideRefreshing() {
        if (sr_list.isRefreshing){
            sr_list.isRefreshing = false
        }
    }

    fun initRecyclerView(){
        rv_list.layoutManager = linearLayoutManager
        rv_list.adapter = adapter

        onLoadMoreListener = object : OnLoadMoreListener(linearLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                onLoadMoreListener.counter = 20
                val nextPage = totalItemsCount + onLoadMoreListener.counter

                showLoadMore()
                presenter.getCountries(nextPage)

                view?.post {
                    adapter.notifyDataSetChanged()
                }
            }
        }

        rv_list.addOnScrollListener(onLoadMoreListener)
    }

    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun showLoadMore() {
        pb_load_more.visibility = View.VISIBLE
    }

    override fun hideLoadMore() {
        pb_load_more.visibility = View.GONE
    }

    override fun showCountries(values: ArrayList<Country>) {
        list.clear()
        list.addAll(values)
        adapter.notifyDataSetChanged()
    }
}
