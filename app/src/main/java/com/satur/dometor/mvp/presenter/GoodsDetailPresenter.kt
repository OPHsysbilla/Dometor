package com.satur.dometor.mvp.presenter

import com.satur.dometor.MyApplication
import com.satur.dometor.base.BasePresenter
import com.satur.dometor.mvp.contract.GoodsDetailContract
import com.satur.dometor.mvp.model.GoodsDetailModel
import com.satur.dometor.mvp.model.bean.ShopListsBean
import com.satur.dometor.net.exception.ExceptionHandle
import com.satur.dometor.utils.NetworkUtil
 
class GoodsDetailPresenter : BasePresenter<GoodsDetailContract.View>(), GoodsDetailContract.Presenter {


    private val goodsDetailModel: GoodsDetailModel by lazy {
        GoodsDetailModel()
    }

    /**
     * 不通过网络，设置视频相关的数据
     */
    override fun loadGoodsDetail(itemInfo: ShopListsBean.Goods) {

        // 检测是否绑定 View
        checkViewAttached()

        /*开启 刷新图标*/
        mRootView?.showLoading()

//        val netType = NetworkUtil.isWifi(MyApplication.context)
        // 当前网络是 Wifi环境 为true
        mRootView?.setGoodsDetail(itemInfo)

        /*结束 刷新图标*/
        mRootView?.dismissLoading()


    }

    /**
     * 通过网络，加载相关商品信息（推荐商品）的数据  （暂时没用）
     */
    override fun requestGoodsDetail(id: Long) {
        mRootView?.showLoading()
        val disposable = goodsDetailModel.requestGoodsDetail(id)
                .subscribe({ shoplist ->
                    mRootView?.apply {
                        dismissLoading()
                        /* 设置相关 */
//                        setRecentRelatedVideo(shoplist.itemList)

                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        setErrorMsg(ExceptionHandle.handleException(t))
                    }
                })

        addSubscription(disposable)

    }


}