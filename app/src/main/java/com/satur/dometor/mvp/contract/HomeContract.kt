package com.satur.dometor.mvp.contract

import com.satur.dometor.base.IBaseView
import com.satur.dometor.base.IPresenter
import com.satur.dometor.mvp.model.bean.HomeBean

/**
 * Created by xuhao on 2017/11/8.
 * 契约类
 */

interface HomeContract {

    interface View : IBaseView {

        /**
         * 设置第一次请求的数据
         */
        fun setHomeData(homeBean: HomeBean)


        /**
         * 显示错误信息
         */
        fun showError(msg: String, errorCode: Int)


    }

    interface Presenter : IPresenter<View> {

        /**
         * 获取首页精选数据
         */
        fun requestHomeData()


    }


}