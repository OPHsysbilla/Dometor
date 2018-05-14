package com.satur.dometor.api

import com.satur.dometor.mvp.model.bean.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by xuhao on 2017/11/16.
 * Api 接口
 */

interface ApiService {

    /**
     * 商品列表 : 根据typeNum获取商品
     */
    @GET("operation/{typeNum}/groups")
    fun getShopListsData(
            @Path("typeNum") typeNum: Int,
            @Query("offset") offset: Int,
            @Query("size") size: Int,
            @Query("opt_type") opt_type: Int = 1): Observable<ShopListsBean>

    /**
     * 首页精选
     */
    @GET("v2/home")
    fun getHomeData(): Observable<HomeBean>

    /**
     * 分类列表 ：获取分类
     */
    /**
     * 获取全部排行榜的Info（包括，title 和 Url）
     */
    @GET("v4/goodscategory")
    fun getGoodsCategoryList(): Observable<TabInfoBean>

    /**
     * 作者信息
     */
    @GET("v4/pgcs/detail/tab?")
    fun getAuthorInfo(@Query("id") id: Long): Observable<AuthorInfoBean>


}