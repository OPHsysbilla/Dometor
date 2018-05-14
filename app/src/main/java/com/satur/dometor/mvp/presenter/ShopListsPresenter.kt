package com.satur.dometor.mvp.presenter

import com.satur.dometor.base.BasePresenter
import com.satur.dometor.mvp.contract.ShopListsContract
import com.satur.dometor.mvp.model.ShopListsModel
import com.satur.dometor.mvp.model.bean.ShopListsBean
import com.satur.dometor.net.exception.ExceptionHandle


/**
 * Created by xuhao on 2017/11/8.
 * 首页精选的 Presenter
 * (数据是 Banner 数据和一页数据组合而成的 HomeBean,查看接口然后在分析就明白了)
 */

class ShopListsPresenter : BasePresenter<ShopListsContract.View>(), ShopListsContract.Presenter {


    private var shopListsBean: ShopListsBean? = null

    private var mOffset: Int = 0
    private val mSize: Int = 10

    private val shopListsModel: ShopListsModel by lazy {

        ShopListsModel()
    }

    override fun requestShopListData(typeNum: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = shopListsModel.requestShopListsData(typeNum = typeNum, offset = mOffset, size = mSize)
                .subscribe({ shoplistsbean ->
                    mRootView?.apply {
                        dismissLoading()
                        shopListsBean = shoplistsbean
                        setShopListData(shopListsBean!!)
                        /*成功请求则往后继续申请mSize个数据*/
                        mOffset = mOffset + mSize

                    }

                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    /**
     * 加载更多
     */

    override fun loadMoreData(typeNum: Int) {
        val disposable = shopListsModel.loadMoreData(typeNum = typeNum, offset = mOffset, size = mSize)
                .subscribe({ shoplistbean ->
                    mRootView?.apply {
                        val newItemList = shoplistbean.goods_list
                        setMoreData(newItemList)
                        /*成功请求则往后继续申请mSize个数据*/
                        mOffset = mOffset + mSize
                    }

                }, { t ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })


        if (disposable != null) {
            addSubscription(disposable)
        }
    }


}