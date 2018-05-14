package com.satur.dometor.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.satur.dometor.R
import com.satur.dometor.base.BaseFragment
import com.satur.dometor.base.BaseFragmentStateAdapter
import com.satur.dometor.mvp.contract.HotTabContract
import com.satur.dometor.mvp.model.bean.TabInfoBean
import com.satur.dometor.mvp.presenter.HotTabPresenter
import com.satur.dometor.net.exception.ErrorStatus
import com.satur.dometor.showToast
import kotlinx.android.synthetic.main.fragment_shop.*

/**
 * Created by xuhao on 2017/11/9.
 * 热门
 */
class ShopFragment : BaseFragment(), HotTabContract.View {


    private val mPresenter by lazy { HotTabPresenter() }

    private var mTitle: String? = null

    /**
     * 存放 tab 标题
     */
    private val mTabTitleList = ArrayList<String>()

    private val mFragmentList = ArrayList<Fragment>()

    companion object {
        fun getInstance(title: String): ShopFragment {
            val fragment = ShopFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }


    override fun getLayoutId(): Int = R.layout.fragment_shop


    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    override fun initView() {

        mLayoutStatusView = multipleStatusView

        toolbar.setBackgroundColor(ContextCompat.getColor(activity as Context, R.color.color_title_bg))
//        iv_search.setOnClickListener { openSearchActivity() }
//        iv_search.setImageResource(R.mipmap.ic_action_search_black)
        //状态栏透明和间距处理
//        StatusBarUtil.darkMode(activity as Activity)
//        StatusBarUtil.setPaddingSmart(activity as Activity, toolbar)
    }

    private fun openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity, iv_search, iv_search.transitionName)
//            startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
        } else {
//            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {

    }

    /**
     * 设置 TabInfo
     */
    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        multipleStatusView.showContent()
        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) { it.name }
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) { RecycleListFragment.getInstance(it.typeNum) }
        mViewPager.adapter = BaseFragmentStateAdapter(childFragmentManager, mFragmentList, tabInfoBean)
//        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager,mFragmentList,mTabTitleList)
        mViewPager.setOffscreenPageLimit(1)
        mTabLayout.setupWithViewPager(mViewPager)

    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}