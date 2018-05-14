package com.satur.dometor.mvp.model

import com.satur.dometor.mvp.model.bean.HomeBean
import com.satur.dometor.net.RetrofitManager
import com.satur.dometor.scheduler.SchedulerUtils
import io.reactivex.Observable

class HomeModel {

    /**
     * 获取首页 Banner 数据
     */
    fun requestHomeData(): Observable<HomeBean> {
        return RetrofitManager.service.getHomeData()
                .compose(SchedulerUtils.ioToMain())
    }

}
