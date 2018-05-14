package com.satur.dometor.mvp.model.bean

import java.io.Serializable

/**
 * desc: 首页 Bean
 */
data class HomeBean(val bannerData: BannerData) {
    /*  首页轮播数据 */
    data class BannerData(val bannerLists: ArrayList<PictureItem>) {
        data class PictureItem(val imageUrl: String, val title: String, val leadToUrl: String?, val action: String?)
    }

    /* 首页入口数据，暂时不用，本地保存修改 */
    data class EntranceLists(val entranceLists: ArrayList<EntranceItem>) : Serializable {
        data class EntranceItem(val iconUrl: String?, val title: String?, val leadToUrl: String?, val action: String?) : Serializable

    }
}