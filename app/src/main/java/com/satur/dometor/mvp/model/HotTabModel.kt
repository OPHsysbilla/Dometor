package com.satur.dometor.mvp.model

import com.satur.dometor.mvp.model.bean.TabInfoBean
import com.satur.dometor.net.RetrofitManager
import com.satur.dometor.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by xuhao on 2017/11/30.
 * desc: 热门 Model
 */
class HotTabModel {

    /**
     * 获取 TabInfo
     */
    fun getTabInfo(): Observable<TabInfoBean> {

        return RetrofitManager.service.getGoodsCategoryList()
                .compose(SchedulerUtils.ioToMain())
    }

}
