package com.satur.dometor.mvp.contract

import com.satur.dometor.base.IBaseView
import com.satur.dometor.base.IPresenter
import com.satur.dometor.mvp.model.bean.HomeBean

/**
 * Created by xuhao on 2017/11/30.
 * desc: 分类详情契约类
 */
interface CategoryDetailContract {

    interface View : IBaseView {
        /**
         *  设置列表数据
         */
        fun setCateDetailList(itemList: ArrayList<HomeBean.BannerData>)

        fun showError(errorMsg: String)


    }

    interface Presenter : IPresenter<View> {

        fun getCategoryDetailList(id: Long)

        fun loadMoreData()
    }
}