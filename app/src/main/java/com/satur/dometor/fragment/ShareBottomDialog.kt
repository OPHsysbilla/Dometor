package com.satur.dometor.fragment

import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.satur.dometor.R
import com.satur.dometor.mvp.model.bean.ShopListsBean
import me.shaohui.bottomdialog.BaseBottomDialog

/*
* 分享弹出框
* */
class ShareBottomDialog : BaseBottomDialog() {

    companion object {
        fun getInstance(itemInfo: ShopListsBean.Goods): ShareBottomDialog {
            val fragment = ShareBottomDialog()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_share
    }

    /* TODO: 完善分享点击逻辑 */
    override fun bindView(v: View?) {

    }


}