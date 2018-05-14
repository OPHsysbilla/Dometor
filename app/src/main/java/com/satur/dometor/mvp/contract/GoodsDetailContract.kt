package com.satur.dometor.mvp.contract

import com.satur.dometor.base.IBaseView
import com.satur.dometor.base.IPresenter
import com.satur.dometor.mvp.model.bean.HomeBean
import com.satur.dometor.mvp.model.bean.ShopListsBean

interface GoodsDetailContract {

    interface View : IBaseView {

        /**
         * 设置商品信息
         */
        fun setGoodsDetail(itemInfo: ShopListsBean.Goods)

        /**
         * 设置最新相关商品
         */
        fun setRecentRelatedGoods(itemList: ArrayList<ShopListsBean.Goods>)

        /**
         * 设置错误信息
         */
        fun setErrorMsg(errorMsg: String)


    }

    interface Presenter : IPresenter<View> {

        /**
         * 加载商品信息
         */
        fun loadGoodsDetail(itemInfo: ShopListsBean.Goods)

        /**
         * 请求相关的商品数据
         */
        fun requestGoodsDetail(id: Long)

    }


}