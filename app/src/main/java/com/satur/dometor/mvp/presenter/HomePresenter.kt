package com.satur.dometor.mvp.presenter

import com.satur.dometor.base.BasePresenter
import com.satur.dometor.mvp.contract.HomeContract
import com.satur.dometor.mvp.model.HomeModel
import com.satur.dometor.mvp.model.bean.HomeBean

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private val homeModel: HomeModel by lazy {

        HomeModel()
    }

    /**
     * 获取首页精选数据 banner 加 一页数据
     */
    override fun requestHomeData() {
        // 检测是否绑定 View
        checkViewAttached()

        /* 模拟首页数据 */
        mRootView?.showLoading()

        val imageUrls = arrayOf("http://img0.imgtn.bdimg.com/it/u=3207029463,1252771303&fm=200&gp=0.jpg"
                , "http://img.hb.aicdn.com/df29349a50d033ba23ac98aa0ae26092f3bcb49b241ca-vvtwNh_fw658"
                , "http://img.hb.aicdn.com/8a3d102056dd331150fef7be2aaf50bacb25b7348d20-3A4hf9_fw658"
                , "http://img.hb.aicdn.com/38c5b24a32f3ce3ed7b5a162d4bedf505b1ce71f8243-z9sXKx_fw658"
        )
        val titles = arrayOf("超越欣赏"
                , "美丽非凡"
                , "新活动开启"
                , "抽象思考")
        val bannerLists = ArrayList<HomeBean.BannerData.PictureItem>()
        for (i in imageUrls.indices) {
            bannerLists.add(HomeBean.BannerData.PictureItem(imageUrl = imageUrls[i], title = titles[i], action = "", leadToUrl = ""))
        }
        val bannerItemList = HomeBean(HomeBean.BannerData(bannerLists))
        mRootView?.setHomeData(bannerItemList)
        mRootView?.dismissLoading()

/*      请求网络真实数据
        val disposable = homeModel.requestHomeData()
                .subscribe({ homeBean->
                    mRootView?.apply {
                        dismissLoading()
                        setHomeData(homeBean)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
*/

    }
}