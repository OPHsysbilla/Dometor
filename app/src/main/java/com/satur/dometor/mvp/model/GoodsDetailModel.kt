package com.satur.dometor.mvp.model

import com.satur.dometor.mvp.model.bean.HomeBean
import com.satur.dometor.mvp.model.bean.ShopListsBean
import com.satur.dometor.net.RetrofitManager
import com.satur.dometor.scheduler.SchedulerUtils
import io.reactivex.Observable



class GoodsDetailModel{

    /**
     * 返回单个商品数据
     */
    fun requestGoodsDetail(id: Long ): Observable<ShopListsBean.Goods> {
        return RetrofitManager.service.getGoodsDetail(id = id)
                .compose(SchedulerUtils.ioToMain())
    }
    /**
     * 返回相关商品数据
     */

//    fun requestRelatedGoods(id: Long ): Observable<ShopListsBean.Goods> {
//        return RetrofitManager.service.getRelatedGoods(id = id)
//                .compose(SchedulerUtils.ioToMain())
//    }

}