package com.satur.dometor.mvp.presenter

import com.satur.dometor.base.BasePresenter
import com.satur.dometor.mvp.contract.HotTabContract
import com.satur.dometor.mvp.model.HotTabModel
import com.satur.dometor.mvp.model.bean.AuthorInfoBean
import com.satur.dometor.mvp.model.bean.TabInfoBean
import com.satur.dometor.net.exception.ExceptionHandle


/**
 * Created by xuhao on 2017/11/30.
 * desc: 获取 TabInfo Presenter
 */
class HotTabPresenter : BasePresenter<HotTabContract.View>(), HotTabContract.Presenter {

    private val mTitles = arrayOf("水果生鲜", "全球海淘", "美妆护肤", "运动户外", "当季女鞋", "服饰箱包", "品牌男装", "家居百货", "环球美食", "家电数码", "家纺家居")

    private val mTypeNums = arrayOf(13, 12, 16, 1451, 1284, 14, 1135, 15, 1, 18, 818)


    private val hotTabModel by lazy { HotTabModel() }


    override fun getTabInfo() {
        checkViewAttached()

        /* 本地模拟分类 */
        val tabInfo = ArrayList<TabInfoBean.Tab>()
        for (i in mTitles.indices) {
            val temp = TabInfoBean.Tab(0, mTitles[i], mTypeNums[i])
            tabInfo.add(temp)
        }

        mRootView?.setTabInfo(TabInfoBean(TabInfoBean.TabInfo(tabInfo)))


        /* 网络请求分类
         mRootView?.showLoading()
        val disposable = hotTabModel.getTabInfo()
                .subscribe({
                    tabInfo->
                    mRootView?.setTabInfo(tabInfo)
                },{
                    throwable->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                })
        addSubscription(disposable)
        */
    }
}