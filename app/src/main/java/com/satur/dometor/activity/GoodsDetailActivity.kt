package com.satur.dometor.activity

import android.graphics.Paint
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.satur.dometor.Constants
import com.satur.dometor.R
import com.satur.dometor.base.BaseActivity
import com.satur.dometor.fragment.AddToCartsBottomDialog
import com.satur.dometor.fragment.ShareBottomDialog
import com.satur.dometor.mvp.contract.GoodsDetailContract
import com.satur.dometor.mvp.model.bean.ShopListsBean
import com.satur.dometor.mvp.presenter.GoodsDetailPresenter
import com.satur.dometor.showToast
import kotlinx.android.synthetic.main.activity_goods_detail.*
import java.text.SimpleDateFormat


class GoodsDetailActivity : BaseActivity(), GoodsDetailContract.View {


    private val normalPriceFormat by lazy {
        resources.getString(R.string.normal_price)
    }
    private val vipPriceFormat by lazy {
        resources.getString(R.string.vip_price)
    }
    private val cntFormat by lazy {
        resources.getString(R.string.goods_cnt)
    }
    private val availableCntFormat by lazy {
        resources.getString(R.string.available_cnt)
    }

    /**
     * 第一次调用的时候初始化
     */
    private val mPresenter by lazy { GoodsDetailPresenter() }


    private val mFormat by lazy { SimpleDateFormat("yyyyMMddHHmmss"); }


    /**
     * Item 详细数据
     */
    private lateinit var itemData: ShopListsBean.Goods

    override fun layoutId(): Int = R.layout.activity_goods_detail

    /**
     * 初始化 View
     */
    override fun initView() {

        mPresenter.attachView(this)

        //状态栏透明和间距处理
//        StatusBarUtil.immersive(this)
//        StatusBarUtil.setPaddingSmart(this, mVideoView)

        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        /***  下拉刷新  ***/
        mRefreshLayout.setOnRefreshListener {
            /* Activity数据传输，没有通过网络请求 */
            loadVideoInfo()
        }
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)

        loadVideoInfo()
    }

    override fun start() {

    }


    /**
     * 初始化数据
     */
    override fun initData() {
        itemData = intent.getSerializableExtra(Constants.BUNDLE_GOODS_DETAIL) as ShopListsBean.Goods
        saveWatchHistoryInfo(itemData)
    }


    /**
     * 保存观看记录
     */
    private fun saveWatchHistoryInfo(watchItem: ShopListsBean.Goods) {
        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
//        val historyMap = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context) as Map<*, *>
//        for ((key, _) in historyMap) {
//            if (watchItem == WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context, key as String)) {
//                WatchHistoryUtils.remove(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context, key)
//            }
//        }
//        WatchHistoryUtils.putObject(Constants.FILE_WATCH_HISTORY_NAME,MyApplication.context, watchItem,"" + mFormat.format(Date()))
    }


    override fun showLoading() {

    }


    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }


    /*
    * 设置商品信息
    * */
    override fun setGoodsDetail(itemInfo: ShopListsBean.Goods) {

        itemData = itemInfo

        /* 文本绑定 */
        goods_place.text = "" + itemData.country
        goods_detail.text = "" + itemData.goods_name
        goods_name.text = "" + itemData.goods_name
        tv_normal_price.text = "" + String.format(normalPriceFormat, itemData.normal_price / 100f
                ?: 999999f)
        tv_vip_price.text = "" + String.format(vipPriceFormat, itemData.market_price / 100f
                ?: 999999f)
        available_cnt.text = "" + String.format(availableCntFormat, itemData.cnt ?: 999999)
        goods_sold.text = "" + String.format(cntFormat, itemData.cnt ?: 999999)
        /* 添加删除线 */
        tv_normal_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG)

        /*选择规格： 商品选定到购物车*/
        goods_check.setOnClickListener { addToShopCarts() }
        btn_addto_cart.setOnClickListener { addToShopCarts() }
        /*轮播图*/
        var bannerImageList = ArrayList<String>()
        /* TODO: 后续添加imgList替换bannerImageList,现在是添加不同的分辨率凑数的 */
        bannerImageList.add(itemData.image_url)
        bannerImageList.add(itemData.thumb_url)
        bannerImageList.add(itemData.hd_thumb_wm)
        var bannerTitleList = ArrayList<String>(bannerImageList.size)

        /* TODO: 是否自动轮播：根据图片数决定，由于模拟的数据只有一张所以就是一张 */
        goods_banner.setAutoPlayAble(bannerImageList.size > 1)
//        goods_banner.setData(bannerImageList,bannerTitleList)
        goods_banner.setData(bannerImageList, bannerTitleList)
        goods_banner.setAdapter(object : BGABanner.Adapter<ImageView, String> {
            override fun fillBannerItem(bgaBanner: BGABanner?, imageView: ImageView?, feedImageUrl: String?, position: Int) {
                Glide.with(this@GoodsDetailActivity)
                        .load(feedImageUrl)
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(imageView!!)
            }
        })


        /*标题栏*/
        toolbar_back.setOnClickListener { finish() }
        toolbar_share.setOnClickListener {
            /* TODO: 分享商品 */
            goToShare()
        }

        // 请求相关的商品信息 (暂时没用)
//        mPresenter.requestRelatedGoods(itemData.goods_id?:0)


    }


    /* TODO: Share分享模块的实际逻辑没有完*/
    private fun goToShare() {
        val dialog = ShareBottomDialog.getInstance(itemData)
        dialog.show(supportFragmentManager)
    }

    private fun addToShopCarts() {
        val dialog = AddToCartsBottomDialog.getInstance(itemData)
        dialog.show(supportFragmentManager)
    }

    // 请求相关的商品信息 (暂时没用)
    override fun setRecentRelatedGoods(itemList: ArrayList<ShopListsBean.Goods>) {

    }

    /**
     * 设置错误信息
     */
    override fun setErrorMsg(errorMsg: String) {
        showToast(errorMsg)
    }


    /**
     *  加载商品信息
     */
    fun loadVideoInfo() {
        mPresenter.loadGoodsDetail(itemData)
    }

    /**
     * 监听返回键
     */
    override fun onBackPressed() {
        finish()
        /*太花了，眼睛受不了这个动画*/
//        overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}