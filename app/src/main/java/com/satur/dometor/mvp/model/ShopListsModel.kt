package com.satur.dometor.mvp.model

import com.satur.dometor.mvp.model.bean.ShopListsBean
import com.satur.dometor.net.RetrofitManager
import com.satur.dometor.scheduler.SchedulerUtils
import io.reactivex.Observable


class ShopListsModel {

    /**
     * 获取首页 Banner 数据
     */
    fun requestShopListsData(typeNum: Int, offset: Int = 0, size: Int = 10): Observable<ShopListsBean> {
        return loadMoreData(typeNum = typeNum, offset = offset, size = size)
    }

    /**
     * 加载更多
     */
    fun loadMoreData(typeNum: Int, offset: Int, size: Int = 10): Observable<ShopListsBean> {
        return RetrofitManager.service.getShopListsData(typeNum, offset = offset, size = size)
                .compose(SchedulerUtils.ioToMain())
    }


}
