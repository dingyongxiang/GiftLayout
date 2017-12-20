# GiftLayout

  高仿斗鱼、虎牙直播礼物控件

 ![](./screen.gif)

 Usage
  -----
  - 在xml布局中引入：
 ```
   <com.yongxiang.giftlayout.giftlayoutlibrary.widget.GiftLayoutView
           android:id="@+id/m_gift_layout"
           android:layout_gravity="center"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content">
       </com.yongxiang.giftlayout.giftlayoutlibrary.widget.GiftLayoutView>
 ```

 - 创建一个礼物对象 实现GiftModelImpl接口
  ```
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
  ```

  - 弹出礼物
   ```
        m_gift_layout.addGiftView(GiftModel)
   ```

  Download
  ----
   Gradle：
   ```
     dependencies {
       implementation 'com.yongxiang.giftlayout.giftlayoutlibrary:giftlayoutlibrary:0.0.4'
     }
   ```
