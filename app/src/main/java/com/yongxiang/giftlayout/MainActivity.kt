package com.yongxiang.giftlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsyncResult
import java.util.concurrent.Future

class MainActivity : AppCompatActivity() {

    private var giftName = listOf<String>()

    private var giftImage  = listOf<Int>()

    private  var giftThread : Future<Any> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {


        giftName = listOf(getString(R.string.gift_1), getString(R.string.gift_2)
                , getString(R.string.gift_3), getString(R.string.gift_4)
                , getString(R.string.gift_5), getString(R.string.gift_6)
                , getString(R.string.gift_7), getString(R.string.gift_8)
                , getString(R.string.gift_9), getString(R.string.gift_10))


        giftImage = listOf(R.mipmap.gift_1, R.mipmap.gift_2
                , R.mipmap.gift_3, R.mipmap.gift_4
                , R.mipmap.gift_5, R.mipmap.gift_6
                , R.mipmap.gift_7, R.mipmap.gift_8
                , R.mipmap.gift_9, R.mipmap.gift_10)

        val mGiftModels = (0..9).map {
            GiftModel(it.toLong(), 1, "", giftImage[it], giftName[it]
                    , "超人不会跑", it.toLong())
        }
        giftThread = doAsyncResult {
            mGiftModels.map {
                Thread.sleep(1000)
                runOnUiThread {
                    m_gift_layout?.addGiftView(it)
                }
            }
        }


    }

    override fun onStop() {
        super.onStop()
        giftThread?.cancel(true)
    }
}
