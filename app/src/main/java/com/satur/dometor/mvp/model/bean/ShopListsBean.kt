package com.satur.dometor.mvp.model.bean

import android.graphics.drawable.Icon
import android.webkit.WebStorage
import java.io.Serializable

/*{
	"server_time": 1524556222,
	"goods_list": [{
		"goods_sn": "1704167441115907",
		"goods_id": 5545919,
		"goods_name": "【虾坏包赔 回味甘甜】【约100只】温州特产九成干烤虾干淡干无添加即食250g零食孕妇宝宝补钙 ",
		"short_name": "烤虾干250g约100只",
		"image_url": "http://omsproductionimg.yangkeduo.com/images/2017-04-16/b66032ae022b9377fd4e93ffd06fe8a1.jpeg",
		"thumb_url": "http://omsproductionimg.yangkeduo.com/images/2017-05-28/ab399dc35cf5009ef307eee2a4738f74.jpeg",
		"hd_thumb_url": "http://omsproductionimg.yangkeduo.com/images/2017-05-28/57a91283ff011a845956e4c276854634.jpeg",
		"is_app": 0,
		"event_type": 0,
		"group": {
			"price": 3900,
			"customer_num": 2
		},
		"cnt": 32330,
		"market_price": 8800,
		"normal_price": 4200,
		"country": "",
		"has_mall_coupon": 0
	}],
	"size": 103
}*/
data class ShopListsBean(val server_time: Long, val goods_list: ArrayList<Goods>, val opt_infos: ArrayList<OptInfo>, val flip: String, val debugInfo: Any?) {
    data class Goods(val goods_id: Long, val goods_name: String, val icon: Icon,
                     val short_name: String, val image_url: String, val thumb_url: String,
                     val hd_thumb_wm: String, val image_wm: String, val event_type: Int,
                     val group: Group, val cnt: Int, val market_price: Int, val normal_price: Int,
                     val country: String?, val tag: Int) : Serializable {

        data class Icon(val width: Int, val id: Int, val url: String?, val height: Int) : Serializable
        data class Group(val price: String, val customer_num: Int) : Serializable

    }

    data class OptInfo(val id: Int, val priority: Int, val opt_name: String) : Serializable

}