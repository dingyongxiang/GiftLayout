package com.yongxiang.giftlayout

import com.yongxiang.giftlayout.giftlayoutlibrary.`interface`.GiftModelImpl

/**
 * @emil dingyongxiang@ipzoe.com
 * @author dyx
 * @date 2017/12/20
 */
data class GiftModel(var id:Long,var level:Int,var imagelUrl:String,var imageResouse:Int,
                     var  name :String,var userName:String,var userId:Long) :GiftModelImpl{
    override fun getGiftId(): Long {
       return id
    }

    override fun getGiftLevel(): Int {
        return level
    }

    override fun getGiftImageUrl(): String {
        return imagelUrl
    }

    override fun getGiftImageResouse(): Int {
        return imageResouse
    }

    override fun getGiftName(): String {
        return name
    }

    override fun getGiftUserName(): String {
        return userName
    }

    override fun getGiftUserId(): Long {
        return userId
    }


}