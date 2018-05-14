package com.satur.dometor.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.satur.dometor.R
import com.satur.dometor.adapter.ShopCategoryAdapter
import com.satur.dometor.adapter.ShopListsAdapter
import com.satur.dometor.base.BaseFragment
import com.satur.dometor.mvp.contract.ShopListsContract
import com.satur.dometor.mvp.model.bean.ShopListsBean
import com.satur.dometor.mvp.presenter.ShopListsPresenter
import com.satur.dometor.net.exception.ErrorStatus
import com.satur.dometor.showToast
import kotlinx.android.synthetic.main.fragment_refresh_recycleview.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RecycleListFragment : BaseFragment(), ShopListsContract.View {


    private val mPresenter by lazy { ShopListsPresenter() }

    private var typeNum: Int = 1

    private var mShopListsAdapter: ShopListsAdapter? = null

    private var loadingMore = false
    private var isRefresh = false

    private var onDestroyViewBefore = false

    companion object {
        fun getInstance(typeNum: Int): RecycleListFragment {
            val fragment = RecycleListFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.typeNum = typeNum
            return fragment
        }
    }
    /*   由于有多个子frament所以不能共用同一个LayoutManager
    private val recyclerLayoutManager by lazy {
//        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        GridLayoutManager(activity,2)
    }
    */


    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    override fun getLayoutId(): Int = R.layout.fragment_refresh_recycleview


    /**
     * 初始化 ViewI
     */
    override fun initView() {
        mPresenter.attachView(this)
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestShopListData(typeNum)
        }
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)

        mRVShopLists.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            // 下拉加载更多
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = mRVShopLists.childCount
                    val itemCount = mRVShopLists.layoutManager.itemCount
                    val firstVisibleItem = (mRVShopLists.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData(typeNum)
                        }
                    }
                }
            }

            //RecyclerView滚动的时候调用
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = (mRVShopLists.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                } else {
                    if (mShopListsAdapter?.mData!!.size > 1) {
                        // 搜索框变颜色
                    }
                }


            }
        })

        mLayoutStatusView = multipleStatusView

        // 右侧ShopLists商品条目 Adapter
        mShopListsAdapter = ShopListsAdapter(activity as Context, ArrayList())
        mRVShopLists.adapter = mShopListsAdapter
        mRVShopLists.layoutManager = GridLayoutManager(activity, 2)
        mRVShopLists.itemAnimator = DefaultItemAnimator()

        //状态栏透明和间距处理
//        StatusBarUtil.darkMode(activity as Activity)
//        StatusBarUtil.setPaddingSmart(activity as Activity, toolbar)

    }


    override fun lazyLoad() {
        mPresenter.requestShopListData(typeNum)
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
    override fun setShopListData(shopListsBean: ShopListsBean) {
        mLayoutStatusView?.showContent()
        Logger.d(shopListsBean)
//        (mRVShopLists.adapter as ShopListsAdapter).addItemData(shopListsBean.goods_list)
        mShopListsAdapter?.addItemData(shopListsBean.goods_list)


    }

    override fun setMoreData(itemList: ArrayList<ShopListsBean.Goods>) {
        loadingMore = false
        mShopListsAdapter?.addItemData(itemList)
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
//        Logger.d(typeNum.toString()+"onDestroy detachView")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyViewBefore = true
    }

    /* Fragment在viewPaper中隐去，会调用onDestroyView而不会真正销毁（不会调用onDestroy, onDetach）
        再次进入时，只会从onCreateView重新开始（前面的onCreate等不会调用）
        所以我们只针对被隐去过的调用过onDestroyView的Fragment进行再次刷新数据操作 */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (onDestroyViewBefore) {

            mPresenter.requestShopListData(typeNum)
            onDestroyViewBefore = false
        }


    }

    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }


}