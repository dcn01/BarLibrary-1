/*
 * Copyright (C) 2017
 * by zhuyk
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zyk.barlibrary

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout

object BarUtil {

    /**
     * 设置状态栏和虚拟按键的背景色或透明,状态栏图标颜色(黑/白)
     * @param statusBarColor 状态栏背景颜色
     * @param navigationBarColor 虚拟按键背景颜色
     * @param statusIconColor  true 状态栏图标为黑色 false 状态栏图标为白色
     */
    fun setStatusAndNavigationBar(activity: Activity, statusBarColor:Int = Color.TRANSPARENT,
                                  navigationBarColor:Int = Color.TRANSPARENT,
                                  statusIconColor:Boolean? = null){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            return
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            setBarShare(activity)
            activity.window.statusBarColor = statusBarColor
            activity.window.navigationBarColor = navigationBarColor
            setRootView(activity)
        }else{
            var isSetRootView = false
            if(statusBarColor === Color.TRANSPARENT){
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                isSetRootView = true
            }else{
                setStatusBarColor(activity,statusBarColor)
                isSetRootView = true
            }
            if(navigationBarColor === Color.TRANSPARENT){
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                isSetRootView = true
            }else{
                //怎么做 不知道
            }
            if (isSetRootView) setRootView(activity)
        }
        if(statusIconColor != null) {
            setDarkStatusIcon(activity, statusIconColor)
        }
    }

    private fun setBarShare(activity: Activity){
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        val oldSystemUiFlags = activity.window.decorView.systemUiVisibility
        var newSystemUiFlags = oldSystemUiFlags or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (newSystemUiFlags != oldSystemUiFlags) {
            activity.window.decorView.systemUiVisibility = newSystemUiFlags
        }
    }

    private fun setStatusBarColor(activity: Activity, statusBarColor:Int){
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        val decorView = activity.window.decorView as ViewGroup
        val fsbView = decorView.findViewById<View>(R.id.fake_status_bar_view_id)
        if (fsbView != null) {
            if (fsbView.visibility == View.GONE) {
                fsbView.visibility = View.VISIBLE
            }
            fsbView.setBackgroundColor(statusBarColor)
        } else {
            // 绘制一个和状态栏一样高的矩形
            val statusBarView = View(activity)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
            statusBarView.layoutParams = params
            statusBarView.setBackgroundColor(statusBarColor)
            statusBarView.id = R.id.fake_status_bar_view_id
            decorView.addView(statusBarView)
        }
    }

    private fun getStatusBarHeight(context: Context):Int{
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    private fun setRootView(activity: Activity){
        val viewGroup = activity.findViewById<ViewGroup>(android.R.id.content)
        val childCount = viewGroup.childCount
        for(i in 0..childCount){
            val childView = viewGroup.getChildAt(i)
            if(childView is ViewGroup){
                childView.fitsSystemWindows = true
                childView.clipToPadding = true
            }
        }
    }

    /**
     * 设置状态栏图标为黑色或者白色
     * @param bDark true 状态栏图标为黑色 false 状态栏图标为白色
     */
    private fun setDarkStatusIcon(activity: Activity, bDark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val decorView = activity.window.decorView
            if (decorView != null) {
                var systemUiFlags = decorView.systemUiVisibility
                if (bDark) {
                    systemUiFlags = systemUiFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    systemUiFlags = systemUiFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
                decorView.systemUiVisibility = systemUiFlags
            }
        }
    }


}