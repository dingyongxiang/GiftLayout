package com.yongxiang.giftlayout.giftlayoutlibrary.`interface`

/**
 * @emil dingyongxiang@ipzoe.com
 * @author dyx
 * @date 2017/12/20
 */
interface GiftModelImpl {


    /**
     * 礼物id
     */
    fun  getGiftId():Long

    /**
     * 礼物等级
     */
    fun getGiftLevel():Int

    /**
     * 礼物图片网络地址
     */
    fun getGiftImageUrl():String

    /**
     * 礼物图片本地地址
     */
    fun getGiftImageResouse():Int

    /**
     * 礼物名称
     */
    fun getGiftName():String

    /**
     * 发送礼物的用户名称
     */
    fun getGiftUserName():String

    /**
     * 发送礼物的用户id
     */
    fun getGiftUserId():Long

}