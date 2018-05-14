package com.satur.dometor.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.satur.dometor.R
import com.satur.dometor.mvp.model.bean.ShopListsBean
import com.satur.dometor.view.recyclerview.ViewHolder
import com.satur.dometor.view.recyclerview.adapter.CommonAdapter


class ShopCategoryAdapter(context: Context, data: ArrayList<ShopListsBean.OptInfo>)
    : CommonAdapter<ShopListsBean.OptInfo>(context, data, -1) {

    var selectPosition: Int = 0

    companion object {
        private val ITEM_TYPE_CONTENT = 1    //item
    }


    /**
     * 设置选中index
     * @param position
     */
    fun setCheckPosition(position: Int) {
        this.selectPosition = position
        notifyDataSetChanged()
    }

    /**
     * 添加更多数据
     */
    fun addItemData(itemList: ArrayList<ShopListsBean.OptInfo>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }

    /**
     * 得到 Item 的类型
     */
    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 ->
                ITEM_TYPE_CONTENT
            else ->
                ITEM_TYPE_CONTENT
        }
    }


    /**
     * 绑定布局
     */
    override fun bindData(holder: ViewHolder, data: ShopListsBean.OptInfo, position: Int) {

        when (getItemViewType(position)) {
        //Text content
            ITEM_TYPE_CONTENT -> {
                val tvTitle = holder.getView<TextView>(R.id.tvCategoryTitle)
                tvTitle.text = "" + mData[position].opt_name ?: ".."

                if (selectPosition != -1) {
                    if (selectPosition == position) {
                        tvTitle.setBackgroundResource(R.drawable.goods_category_list_bg_select)
                    } else {
                        tvTitle.setBackgroundResource(R.drawable.goods_category_list_bg_normal)
                    }
                } else {
                    tvTitle.setBackgroundResource(R.drawable.goods_category_list_bg_normal)
                }


            }
        }
    }

    /**
     *  创建布局
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflaterView(R.layout.item_goods_category_title, parent))
    }

    /**
     * 加载布局
     */
    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        //创建view
        val view = mInflater?.inflate(mLayoutId, parent, false)
        return view!!
    }

}
