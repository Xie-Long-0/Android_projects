package com.example.util;

import android.content.Context;

public class Utils {
    // dp长度转换为px
    public static int dp2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
