package com.yongxiang.giftlayout.giftlayoutlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;

/**
 * dingyongxiang@ipzoe.com
 * Created by ding'yong'xiang on 2017/7/3.
 */
public final class DensityUtils {

    /**
     * 四舍五入
     */
    private static final float DOT_FIVE = 0.5f;

    /**
     * Private constructor to prohibit nonsense instance creation.
     */
    private DensityUtils() {
    }

    /**
     * dip转换成px
     *
     * @param context Context
     * @param dip     dip Value
     * @return 换算后的px值
     */
    public static int dip2px(Context context, float dip) {
        float density = getDensity(context);
        return (int) (dip * density + DensityUtils.DOT_FIVE);
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * px转换成dip
     *
     * @param context Context
     * @param px      px Value
     * @return 换算后的dip值
     */
    public static int px2dip(Context context, float px) {
        float density = getDensity(context);
        return (int) (px / density + DOT_FIVE);
    }

    /**
     * DisplayMetrics 对象
     */
    private static DisplayMetrics sDisplayMetrics;

    /**
     * 得到显示宽度
     *
     * @param context Context
     * @return 宽度
     */
    public static int getDisplayWidth(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.widthPixels;
    }

    /**
     * 得到显示高度
     *
     * @param context Context
     * @return 高度
     */
    public static int getDisplayHeight(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.heightPixels;
    }

    /**
     * 得到显示密度
     *
     * @param context Context
     * @return 密度
     */
    public static float getDensity(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.density;
    }

    /**
     * 得到DPI
     *
     * @param context Context
     * @return DPI
     */
    public static int getDensityDpi(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.densityDpi;
    }

    /**
     * 初始化DisplayMetrics
     *
     * @param context Activity
     */
    private static synchronized void initDisplayMetrics(Context context) {
        sDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    /**
     * 当前是否为横屏模式
     *
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 当前是否为竖屏模式
     *
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }


    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getDaoHangHeight(Context context) {
        if (!isNavigationBarShow((Activity) context)) {
            return 0;
        }
        int resourceId;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");

            return context.getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }


    public static int getNavigationBarHeight(Activity activity) {
        if (!isNavigationBarShow(activity)) {
            return 0;
        }
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static boolean isNavigationBarShow(Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        Point realSize = new Point();
        display.getSize(size);
        display.getRealSize(realSize);

        if (isLandscape(activity)) {
            return realSize.x != size.x;
        } else {
            return realSize.y != size.y;
        }
    }


}