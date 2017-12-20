package com.yongxiang.giftlayout.giftlayoutlibrary.widget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.yongxiang.giftlayout.giftlayoutlibrary.R
import com.yongxiang.giftlayout.giftlayoutlibrary.`interface`.GiftModelImpl
import com.yongxiang.giftlayout.giftlayoutlibrary.utils.DensityUtils
import java.util.Timer
import java.util.TimerTask

/**
 * dingyongxiang@ipzoe.com
 * Created by ding'yong'xiang on 2017/10/13.
 */

class GiftLayoutView : LinearLayout {

    /**
     * 动画相关
     */
    private var giftNumAnim: NumAnim? = null
    private var timer: Timer? = null
    private val transition = LayoutTransition()
    private val maxGift = 2 // 最大显示礼物个数


    constructor(context: Context) : super(context) {
        initGiftView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initGiftView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initGiftView()
    }

    private fun initGiftView() {
        orientation = LinearLayout.VERTICAL
        giftNumAnim = NumAnim()
        val animIn = ObjectAnimator.ofFloat( this,"translationX", -1200f, 0f)
        animIn.duration = 800
        transition.setAnimator(LayoutTransition.APPEARING, animIn)

        val animOut = ObjectAnimator.ofFloat(this, "alpha", 1f, 0.8f, 0f)
        animOut.duration = 200
        transition.setAnimator(LayoutTransition.DISAPPEARING, animOut)
        layoutTransition = transition

        clearTiming()
    }

    /**
     * 显示礼物的方法
     */
    fun addGiftView(giftModel: GiftModelImpl) {
        val tag = "${giftModel.getGiftUserId()}_+${giftModel.getGiftId()}"
        Log.d("GiftLayoutView", "addGiftView: " + tag)
        var giftView: View? = findViewWithTag(tag)
        if (giftView == null) {/*该用户不在礼物显示列表*/
            val childCount = childCount
            // 如果正在显示的礼物的个人超过最大礼物个数
            // 那么就移除最后一次更新时间比较长的
            if (childCount >= maxGift) {
                var minTime: Long = 0
                var minPosition = 0
                for (i in 0 until childCount) {
                    val nowTime = getChildAt(i).findViewById<View>(R.id.m_gift_text_root).tag as Long
                    if (i == 0) {
                        minTime = nowTime
                        minPosition = i
                    } else {
                        if (nowTime < minTime) {
                            minTime = nowTime
                            minPosition = i
                        }
                    }
                }
                removeGiftView(minPosition)

            }
            // 如果礼物类型是大礼物 得到大礼物布局
            if (giftModel.getGiftLevel() > 1) {
                //获取大礼物的View的布局
                giftView = addBigGiftView()
            } else {
                //获取小礼物的View的布局
                giftView = addSmallGiftView()
            }
            giftView.tag = tag/*设置view标识*/

            val giftTextRoot = giftView.findViewById(R.id.m_gift_text_root) as LinearLayout
            val userName = giftView.findViewById(R.id.m_gift_user_name) as TextView
            val giftName = giftView.findViewById(R.id.m_gift_name) as TextView
            val giftIcon = giftView.findViewById(R.id.m_gift_icon) as ImageView
            userName.text = giftModel.getGiftUserName()
            giftName.text = giftModel.getGiftName()
            if (giftModel.getGiftImageUrl() != "") {
                Glide.with(context).load(giftModel.getGiftImageUrl()).into(giftIcon)
            } else if (giftModel.getGiftImageResouse() != 0) {
                Glide.with(context).load(giftModel.getGiftImageResouse()).into(giftIcon)
            }

            val giftNum = giftView.findViewById(R.id.giftNum) as MagicTextView/*找到数量控件*/
            giftNum.text = "x1"/*设置礼物数量*/
            giftTextRoot.tag = System.currentTimeMillis()/*设置时间标记*/
            giftNum.tag = 1/*给数量控件设置标记*/
            addView(giftView)/*将礼物的View添加到礼物的ViewGroup中*/
        } else {
            // 修改礼物数量
            val giftNum = giftView.findViewById(R.id.giftNum) as MagicTextView
            val showNum = giftNum.tag as Int + 1
            giftNum.text = "x$showNum"
            giftNum.tag = showNum
            // 给view设置时间标志位
            val giftTextRoot = giftView.findViewById(R.id.m_gift_text_root) as LinearLayout
            giftTextRoot.tag = System.currentTimeMillis()
            giftNumAnim!!.start(giftNum)
        }

    }

    /**
     * 添加大礼物view,(考虑垃圾回收)
     */
    private fun addBigGiftView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_big_gift_view, null)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 90))
        lp.topMargin = 10
        view.layoutParams = lp
        return view
    }

    /**
     * 添加小礼物view,(考虑垃圾回收)
     */
    private fun addSmallGiftView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_small_gift_view, null)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DensityUtils.dp2px(context, 80))
        lp.topMargin = 10
        view.layoutParams = lp
        return view
    }

    /**
     * 删除礼物view
     */
    private fun removeGiftView(index: Int) {
        val removeView = getChildAt(index)
        Log.d("GiftLayoutView", "removeGiftView: " + index)
        removeView.post { removeViewAt(index) }
    }

    /**
     * 数字放大动画
     */
    private inner class NumAnim {
        private var lastAnimator: Animator? = null

        fun start(view: View) {
            if (lastAnimator != null) {
                lastAnimator!!.removeAllListeners()
                lastAnimator!!.end()
                lastAnimator!!.cancel()
            }
            val anim1 = ObjectAnimator.ofFloat(view, "scaleX", 1.3f, 1.0f)
            val anim2 = ObjectAnimator.ofFloat(view, "scaleY", 1.3f, 1.0f)
            val animSet = AnimatorSet()
            lastAnimator = animSet
            animSet.duration = 800
            animSet.interpolator = OvershootInterpolator()
            animSet.playTogether(anim1, anim2)
            animSet.start()
        }
    }

    /**
     * 定时清除礼物
     */
    private fun clearTiming() {

        val task = object : TimerTask() {
            override fun run() {
                val count = childCount
                val nowtime = System.currentTimeMillis()
                for (i in 0 until count) {
                    val upTime = getChildAt(i).findViewById<View>(R.id.m_gift_text_root).tag as? Long
                    upTime?.let {
                        if (nowtime - upTime >= 3000) {
                            removeGiftView(i)
                            return
                        }
                    } ?: return
                }
            }
        }
        timer = Timer()
        timer!!.schedule(task, 3000, 500)
    }

    /**
     * 动态修改礼物父布局的高度
     */
    /*private fun dynamicChangeGiftParentH(showhide: Boolean) {
        if (showhide) {
            if (childCount != 0) {
                *//*判断是否有礼物显示，如果有就修改父布局高度，如果没有就不作任何操作*//*
                val layoutParams = layoutParams
                layoutParams.height = getChildAt(0).height
                setLayoutParams(layoutParams)
            }
        } else {*//*如果软键盘隐藏中*//*
            *//*就将装载礼物的容器的高度设置为包裹内容*//*
            val layoutParams = layoutParams
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            setLayoutParams(layoutParams)
        }
    }*/

    override fun onDetachedFromWindow() {
        if (timer != null)
            timer!!.cancel()
        super.onDetachedFromWindow()
    }
}
