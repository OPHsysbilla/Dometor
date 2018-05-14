package com.satur.dometor.mvp.model.bean

import java.io.Serializable

/**
 * Created by xuhao on 2017/11/29.
 * desc:分类 Bean
 */
data class CategoryBean(val groupId: Long, val name: String, val description: String) : Serializable
