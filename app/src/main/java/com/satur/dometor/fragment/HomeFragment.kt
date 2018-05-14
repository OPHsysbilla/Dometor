package com.satur.dometor.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.orhanobut.logger.Logger
import com.satur.dometor.R
import com.satur.dometor.base.BaseFragment
import com.satur.dometor.mvp.contract.HomeContract
import com.satur.dometor.mvp.model.bean.HomeBean
import com.satur.dometor.mvp.presenter.HomePresenter
import com.satur.dometor.net.exception.ErrorStatus
import com.satur.dometor.showToast
import com.satur.multiple_status_view.MultipleStatusView
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment(), HomeContract.View {


    private val mPresenter by lazy { HomePresenter() }

    private var mTitle: String? = null

    private var loadingMore = false

    private var isRefresh = false
    private var mMaterialHeader: MaterialHeader? = null


    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }


    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }


    override fun getLayoutId(): Int = R.layout.fragment_home


    /**
     * 初始化 ViewI
     */
    override fun initView() {
        mPresenter.attachView(this)
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData()
        }
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)


        /*RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = mRecyclerView.childCount
                    val itemCount = mRecyclerView.layoutManager.itemCount
                    val firstVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            //RecyclerView滚动的时候调用
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mHomeAdapter?.mData!!.size > 1) {
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHomeAdapter!!.mData
                        val item = itemList[currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }


            }
        })*/



        mLayoutStatusView = multipleStatusView as MultipleStatusView

        //状态栏透明和间距处理
//        StatusBarUtil.darkMode(activity)
//        StatusBarUtil.setPaddingSmart(activity, toolbar)

    }

    fun setBanner(bannerImageList: ArrayList<String>, bannerTitleList: ArrayList<String>) {
        //设置 banner
//        Logger.d(bannerImageList)
//        Logger.d(bannerTitleList)
        goods_banner.setAutoPlayAble(bannerImageList.size > 1)
        goods_banner.setData(bannerImageList, bannerTitleList)
        goods_banner.setAdapter(object : BGABanner.Adapter<ImageView, String> {
            override fun fillBannerItem(bgaBanner: BGABanner?, imageView: ImageView?, feedImageUrl: String?, position: Int) {
                Glide.with(activity as Context)
                        .load(feedImageUrl)
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(imageView!!)

            }
        })
        //没有使用到的参数在 kotlin 中用"_"代替
//        home_banner.setDelegate { _, imageView, _, i ->
////            switch(i){}
//        }

    }

    override fun lazyLoad() {
        mPresenter.requestHomeData()
    }


    /**
     * 显示 Loading （下拉刷新的时候不需要显示 Loading）
     */
    override fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    /**
     * 隐藏 Loading
     */
    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    /**
     * 设置首页数据
     */
    override fun setHomeData(homeBean: HomeBean) {
        mLayoutStatusView?.showContent()
        Logger.d(homeBean)
        /* 设置轮播图片 */
        var bannerImageList = ArrayList<String>()
        var bannerTitleList = ArrayList<String>()

        homeBean.bannerData.bannerLists.mapTo(bannerImageList) { it.imageUrl }
        homeBean.bannerData.bannerLists.mapTo(bannerTitleList) { it.title }
        setBanner(bannerImageList = bannerImageList, bannerTitleList = bannerTitleList)
    }


    /**
     * 显示错误信息
     */
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }


}
