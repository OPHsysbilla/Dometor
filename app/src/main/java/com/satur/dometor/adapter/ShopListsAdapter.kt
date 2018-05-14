package com.satur.dometor.adapter

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.satur.dometor.R
import com.satur.dometor.mvp.model.bean.ShopListsBean
import com.satur.dometor.view.recyclerview.ViewHolder
import com.satur.dometor.view.recyclerview.adapter.CommonAdapter


class ShopListsAdapter(context: Context, data: ArrayList<ShopListsBean.Goods>)
    : CommonAdapter<ShopListsBean.Goods>(context, data, -1) {


    companion object {
        private val ITEM_TYPE_SHOP_CONTENT = 1    //title & gridview 类型
    }

    private val normalPriceFormat by lazy {
        mContext.resources.getString(R.string.normal_price)
    }
    private val vipPriceFormat by lazy {
        mContext.resources.getString(R.string.vip_price)
    }
    private val cntFormat by lazy {
        mContext.resources.getString(R.string.goods_cnt)
    }

    /**
     * 添加更多数据
     */
    fun addItemData(itemList: ArrayList<ShopListsBean.Goods>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }

    /**
     * 得到 Item 的类型
     */
    override fun getItemViewType(position: Int): Int {
        return ITEM_TYPE_SHOP_CONTENT
    }

    /**
     * 绑定布局
     */
    override fun bindData(holder: ViewHolder, data: ShopListsBean.Goods, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_SHOP_CONTENT -> {
                holder.setText(R.id.tv_short_title, mData[position].short_name ?: "")
                holder.setText(R.id.tv_vip_price, String.format(vipPriceFormat, mData[position].market_price
                / 100f ?: 999999f))
                holder.setText(R.id.tv_normal_price, String.format(normalPriceFormat, mData[position].normal_price
                / 100f ?: 999999f))
                holder.setText(R.id.tv_goods_cnt, String.format(cntFormat, mData[position].cnt
                        ?: 99999))
                /* 添加删除线 */
                holder.getView<TextView>(R.id.tv_normal_price).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG)


                holder.setImagePath(R.id.iv_short_cover, object : ViewHolder.HolderImageLoader(mData[position].thumb_url) {
                    override fun loadImage(iv: ImageView, path: String) {
                        // 加载封页图
                        Glide.with(mContext)
                                .load(path)
                                .transition(DrawableTransitionOptions().crossFade())
                                .into(holder.getView(R.id.iv_short_cover))
                    }

                })


            }

        }
    }

    /**
     *  创建布局
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflaterView(R.layout.item_shop_content, parent))

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
