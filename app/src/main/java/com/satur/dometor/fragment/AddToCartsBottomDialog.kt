package com.satur.dometor.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.satur.dometor.R
import com.satur.dometor.mvp.model.bean.ShopListsBean
import kotlinx.android.synthetic.main.fragment_addto_carts.*
import me.shaohui.bottomdialog.BaseBottomDialog

class AddToCartsBottomDialog : BaseBottomDialog() {

    var selectNum : Int = 1

    companion object {
        lateinit var itemData: ShopListsBean.Goods
        fun getInstance(itemInfo: ShopListsBean.Goods): AddToCartsBottomDialog {
            val fragment = AddToCartsBottomDialog()
            itemData = itemInfo

            return fragment
        }
    }

    private val availableCntFormat by lazy {
        resources.getString(R.string.available_cnt)
    }
    private val vipPriceFormat by lazy {
        resources.getString(R.string.vip_price)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_addto_carts
    }

    /*kotlin是自动绑定控件的，java需要手动绑定，所以不需要在bingView中添加任何代码*/
    override fun bindView(v: View?) {
        /*保持为空函数体*/
        /*在onViewCreated及其后引用控件即可*/
    }

    /*此函数填写控件的操作逻辑*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_add.setOnClickListener{
            selectNum++
            notifyIcon()
        }
        iv_remove.setOnClickListener{
            selectNum--
            if (selectNum<1)selectNum = 1
            else if(selectNum>=999999)selectNum = 999999
            notifyIcon()
        }
        Glide.with(activity as Context)
                .load(itemData.thumb_url)
                .transition(DrawableTransitionOptions().crossFade())
                .into(iv_goods_small)
        tv_vip_price.text = "" + String.format(vipPriceFormat, itemData.market_price / 100f ?: 999999f)
        available_cnt.text = "" + String.format(availableCntFormat, itemData.cnt ?: 999999)
        close_addto_carts.setOnClickListener{ dismiss() }
        submit_addtocarts.setOnClickListener{
            /*TODO: 加入购物车*/

        }

    }

//    override fun getHeight(): Int {
//        return 160
//    }

    /*更新数字*/
    private fun notifyIcon() {
        tv_selected_num.text = ""+selectNum
    }


}