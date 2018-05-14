package com.satur.dometor.mvp.contract

import com.satur.dometor.base.IBaseView
import com.satur.dometor.base.IPresenter
import com.satur.dometor.mvp.model.bean.ShopListsBean

interface ShopListsContract {

    interface View : IBaseView {

        /**
         * 设置第一次请求的数据
         */
        fun setShopListData(shopListsBean: ShopListsBean)

        /**
         * 设置加载更多的数据
         */
        fun setMoreData(itemList: ArrayList<ShopListsBean.Goods>)

        /**
         * 显示错误信息
         */
        fun showError(msg: String, errorCode: Int)


    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取首页精选数据
         */
        fun requestShopListData(typeNum: Int)

        /**
         * 加载更多数据
         */
        fun loadMoreData(typeNum: Int)


    }

}