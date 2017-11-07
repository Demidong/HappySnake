package com.dx.demi.snake.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Demi on 2015/4/15.
 */
public class AndroidUtil {
    public static boolean isCurrentActivity(Context context, String cls) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getClassName().equals(cls)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 从assets读取文件
     *
     * @param fileName
     * @param resource
     * @return
     */
    public static String getFromAssets(String fileName, Resources resource) {
        try {
            InputStreamReader inputReader = new InputStreamReader(resource.getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            inputReader.close();
            bufReader.close();
            return Result;
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 是否是当前app
     *
     * @param context
     * @return
     */
    public static boolean isCurrentApp(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getClassName().contains(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 跳转拨号界面
     *
     * @param phoneNum
     * @param context
     */
    public static void onDial(String phoneNum, Context context) {
        Intent callIntent = new Intent();
        callIntent.setAction(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNum));
        context.startActivity(callIntent);
    }

    /**
     * 打电话
     *
     * @param phoneNum
     * @param context
     */
    public static void onCall(String phoneNum, Context context) {
        Intent callIntent = new Intent();
        callIntent.setAction(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNum));
        context.startActivity(callIntent);
    }

    /**
     * send message<br/>
     * fixed bug in android pad
     *
     * @param smsBody
     * @param context
     */
    public static void sendMessage(final String smsBody, final Context context) {
        if (context == null)
            return;
        try {
            Uri sendSmsTo = Uri.parse("smsto:");
            Intent intent = new Intent(Intent.ACTION_SENDTO, sendSmsTo);
            intent.putExtra("sms_body", smsBody);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置字体大小
     *
     * @param textView
     * @param str1
     * @param size1
     * @param str2
     * @param size2
     */
    public static void setTextSize(TextView textView, String str1, int size1, String str2, int size2) {
        SpannableStringBuilder style = new SpannableStringBuilder(str1 + str2);
        style.setSpan(new AbsoluteSizeSpan(size1, true), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(size2, true), str1.length(), str1.length() + str2.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(style);
    }

    /**
     * 设置文本风格
     *
     * @param str1
     * @param size1
     * @param color1
     * @param str2
     * @param size2
     * @param color2
     * @return
     */
    public static SpannableStringBuilder setTextStyle(String str1, int size1, int color1, String str2, int size2, int color2) {
        SpannableStringBuilder style = new SpannableStringBuilder(str1 + str2);
        style.setSpan(new AbsoluteSizeSpan(size1, true), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(size2, true), str1.length(), str1.length() + str2.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color1), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color2), str1.length(), str1.length() + str2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    /**
     * 设置TextView 颜色和文字大小 注意：保持后面的数组大小一样
     *
     * @param textView
     * @param strs
     * @param colors
     * @param textSizes
     */
    public static void setTextSizeColor(TextView textView, String[] strs, int[] colors, int[] textSizes) {
        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            builder.append(str);
        }
        SpannableStringBuilder style = new SpannableStringBuilder(builder.toString());
        int count = strs.length;
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < count; i++) {
            endIndex += strs[i].length();
            if (colors != null) {
                style.setSpan(new ForegroundColorSpan(colors[i]), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (textSizes != null) {
                style.setSpan(new AbsoluteSizeSpan(textSizes[i], true), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            startIndex += strs[i].length();
        }
        textView.setText(style);
    }

    /**
     * 获得屏幕参数
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics();
    }

    /**
     * 像素转换成屏幕密度
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 屏幕密度转换成像素;
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static void resetViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(width, height);
        } else {
            lp.width = width;
            lp.height = height;
        }
        view.setLayoutParams(lp);
    }

    /**
     * 设置View高
     *
     * @param view
     * @param height
     */
    public static void resetViewHeight(View view, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * 设置View宽
     *
     * @param view
     * @param width
     */
    public static void resetViewWidth(View view, int width) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        view.setLayoutParams(lp);
    }


    /**
     * 验证手机号码格式
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[025-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 强制关闭输入法
     */
    public static void hide(Activity activity) {
        hide(activity.getWindow().getCurrentFocus());
    }

    public static void hide(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getVersionName(final Context context) {
        // get package manager instance
        PackageManager packageManager = context.getPackageManager();
        // getPackageName() 0 is current version name
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (packInfo == null) {
            return "";
        }
        return packInfo.versionName;
    }

    public static int getVersionCode(final Context context) {
        // get package manager instance
        PackageManager packageManager = context.getPackageManager();
        // getPackageName() 0 is current version name
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (packInfo == null) {
            return 1;
        }
        return packInfo.versionCode;
    }

    //get system version name
    public static String getOSVersionName() {
        return Build.MODEL + "/" + Build.VERSION.RELEASE + "/" + Build.VERSION.SDK_INT;
    }

    /**
     * 网络是否可用
     *
     * @param appContext
     * @return
     */
    public static boolean isNetworkAvailable(final Context appContext) {
        if (appContext == null) {
            // cannot detect, assume network is available
            return true;
        }
        Context context = appContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断夫妇是否启动
     *
     * @param serviceClass
     * @param context
     * @return
     */
    public static boolean isServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (serviceList == null || serviceList.size() == 0)
            return false;
        for (ActivityManager.RunningServiceInfo info : serviceList) {
            if (info.service.getClassName().equals(serviceClass.getName()))
                return true;
        }
        return false;
    }

    public static boolean isServiceRunning(String serviceName, Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (serviceList == null || serviceList.size() == 0)
            return false;
        for (ActivityManager.RunningServiceInfo info : serviceList) {
            if (info.service.getClassName().equals(serviceName))
                return true;
        }
        return false;
    }

    /**
     * 解锁屏幕 点亮屏幕
     *
     * @param mActivity
     */
    public static void disableKeyguardWakeScreen(Activity mActivity) {
        PowerManager pm = (PowerManager) mActivity.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock m_wakeObj = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "");
        //点亮屏幕10秒钟
        m_wakeObj.acquire(1000 * 10);
        m_wakeObj.setReferenceCounted(false);
        if (m_wakeObj.isHeld())
            m_wakeObj.release();//释放资源
        KeyguardManager keyguardManager = (KeyguardManager) mActivity.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
        keyguardLock.disableKeyguard();
        int flag = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mActivity.getWindow().addFlags(flag);
    }

    /**
     * 获取application节点下meta_data值
     *
     * @param context
     * @param key
     * @return
     */
    public static String getApplicationMetaData(Context context, String key) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static String GetNetworkType(Context context) {
        String strNetworkType = "";

        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }

            }
        }

        return strNetworkType;
    }


    public static String removeAllImgTag(String htmlContent) {
        if (htmlContent == null) {
            return "";
        }
        String imageTag = "<(img|IMG)(.*?)(/>|></img>|>)";
        return htmlContent.replaceAll(imageTag, "");
    }

    public static String addFormatBeforeATag(Context context, String htmlContent) {
        if (htmlContent == null) {
            return "";
        }
        htmlContent = filterContent(htmlContent);
        final String link_icon = "<img src=\"ic_link_blue_5\"/>";
        return htmlContent.replaceAll("<a", link_icon + "<a");
    }

    private static String filterContent(String htmlContent) {
        String pattern1 = "<div>\\s*<br>\\s*<\\/div>";
        String str1 = "<div></div>";

        String pattern2 = "<div>\\s*<br>\\s*<div>";
        String str2 = "<div><div>";

        String pattern3 = "<\\/div>\\s*<br>\\s*<\\/div>";
        String str3 = "</div></div>";

        String pattern4 = "<div></div>";
        String str4 = "";

        String pattern5 = "<p><br>\\s*</p>\\s*";
        String str5 = "";

        String pattern6 = "<p>\\s*</p>\\s*";
        String str6 = "";

        Pattern r1 = Pattern.compile(pattern1);
        Matcher m1 = r1.matcher(htmlContent);
        htmlContent = m1.replaceAll(str1);

        Pattern r2 = Pattern.compile(pattern2);
        Matcher m2 = r2.matcher(htmlContent);
        htmlContent = m2.replaceAll(str2);

        Pattern r3 = Pattern.compile(pattern3);
        Matcher m3 = r3.matcher(htmlContent);
        htmlContent = m3.replaceAll(str3);

        Pattern r4 = Pattern.compile(pattern4);
        Matcher m4 = r4.matcher(htmlContent);
        htmlContent = m4.replaceAll(str4);

        Pattern r5 = Pattern.compile(pattern5);
        Matcher m5 = r5.matcher(htmlContent);
        htmlContent = m5.replaceAll(str5);

        Pattern r6 = Pattern.compile(pattern6);
        Matcher m6 = r6.matcher(htmlContent);
        htmlContent = m6.replaceAll(str6);
        return htmlContent;
    }

    private static Toast mToast;

    public static void showToast(Context context, String text) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (context != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            return windowManager.getDefaultDisplay().getWidth();
        } else {
            return 0;
        }
    }
}
