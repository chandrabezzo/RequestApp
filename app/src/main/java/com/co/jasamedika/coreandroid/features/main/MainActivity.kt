package com.co.jasamedika.coreandroid.features.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.co.jasamedika.coreandroid.R
import com.co.jasamedika.coreandroid.adapter.recyclerView.KaryawanRVAdapter
import com.co.jasamedika.coreandroid.adapter.spinner.JabatanSPAdapter
import com.co.jasamedika.coreandroid.data.model.user.User
import com.co.jasamedika.coreandroid.base.BaseActivity
import com.co.jasamedika.coreandroid.data.model.general.Karyawan
import com.co.jasamedika.coreandroid.data.model.jabatan.Jabatan
import com.co.jasamedika.coreandroid.data.model.user.Socmed
import com.co.jasamedika.coreandroid.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContracts.View {

    @Inject
    lateinit var presenter : MainPresenter<MainContracts.View>
    @Inject
    lateinit var linearLayoutManager : LinearLayoutManager

    lateinit var spAdapter : JabatanSPAdapter
    var allJabatan = ArrayList<Jabatan>()
    var allKaryawan = ArrayList<Karyawan>()
    lateinit var rvAdapter : KaryawanRVAdapter
    lateinit var onLoadMoreListener : OnLoadMoreListener

    override fun onInitializedView(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        presenter.onAttach(this)

        setActionBarTitle(getString(R.string.beranda))
        displayHome()

        spAdapter = JabatanSPAdapter(this, allJabatan)
        sp_list.adapter = spAdapter

        rvAdapter = KaryawanRVAdapter(this, allKaryawan)

        initRecyclerView()

        presenter.getUserApi()
        presenter.getAllJabatan()
        presenter.getJabatanApi()
        presenter.getAllKaryawan()
        presenter.getKaryawanApi()
        presenter.getSocmed()
        presenter.getSocmedApi()

        sr_list.setOnRefreshListener {
            presenter.getKaryawanApi()
        }
    }

    override fun hideRefreshing() {
        if (sr_list.isRefreshing){
            sr_list.isRefreshing = false
        }
    }

    fun initRecyclerView(){
        rv_list.layoutManager = linearLayoutManager
        rv_list.adapter = rvAdapter

        onLoadMoreListener = object : OnLoadMoreListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                onLoadMoreListener.counter = 5
                val nextPage = totalItemsCount + onLoadMoreListener.counter

                showLoadMore()
                presenter.loadMoreKaryawan(nextPage)
                val curSize = rvAdapter.itemCount
                view?.post {
                    rvAdapter.notifyItemRangeInserted(curSize, allKaryawan.size - 1)
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

    override fun showUser(user: User) {
        tv_user.text = user.nama + " - " + user.jabatan
        var rt = user.alamat!!.rt
        var rw = user.alamat!!.rw
        var kec = user.alamat!!.kecamatan
        var kab = user.alamat!!.kabupaten

        tv_alamat.text = "RT $rt / RW $rw, $kec, $kab"
    }

    override fun showJabatan(jabatan: List<Jabatan>) {
        spAdapter.update(jabatan)
    }

    override fun showLoadMore() {
        pb_load_more.visibility = View.VISIBLE
    }

    override fun hideLoadMore() {
        pb_load_more.visibility = View.GONE
    }

    override fun showKaryawan(values: List<Karyawan>) {
        allKaryawan.clear()
        allKaryawan.addAll(values)
        rvAdapter.notifyDataSetChanged()
    }

    override fun showSocmed(value: Socmed) {
        tv_socmed.text = value.email
    }
}
