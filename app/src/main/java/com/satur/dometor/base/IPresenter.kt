package com.satur.dometor.base

import com.satur.dometor.mvp.model.bean.HomeBean


/**
 * @author Jake.Ho
 * created: 2017/10/25
 * desc: Presenter 基类
 */


interface IPresenter<in V : IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}
