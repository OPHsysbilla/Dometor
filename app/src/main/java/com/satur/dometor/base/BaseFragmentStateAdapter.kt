package com.satur.dometor.base

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup
import com.satur.dometor.mvp.model.bean.TabInfoBean

class BaseFragmentStateAdapter : FragmentStatePagerAdapter {
    /* List是不可变的 */
    var fragmentList: List<Fragment>? = ArrayList()
    var mTabInfoBean: TabInfoBean? = null
    var mCurFragment: Fragment? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm) {
        this.fragmentList = fragmentList
    }

    constructor(fm: FragmentManager, fragmentList: List<Fragment>, mTabInfoBean: TabInfoBean) : super(fm) {
        this.mTabInfoBean = mTabInfoBean
        this.fragmentList = fragmentList
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTabInfoBean!!.tabInfo.tabList.get(position).name ?: ""
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList!!.get(position)
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

    /* POSITION_NONE表示每次都是新的Fragment */
    override fun getItemPosition(`object`: Any): Int {
//        return super.getItemPosition(`object`)
        return PagerAdapter.POSITION_NONE
    }

}