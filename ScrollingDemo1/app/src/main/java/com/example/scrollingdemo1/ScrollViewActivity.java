package com.example.scrollingdemo1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.util.Utils;

public class ScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);

        LinearLayout layout = findViewById(R.id.linear_scroll_view);
        Button button_goto = findViewById(R.id.button_goto);
        Button button_add_text = findViewById(R.id.button_add_text);
        Button button_del_text = findViewById(R.id.button_del_text);
        LinearLayout layout_for_button = findViewById(R.id.layout_for_button);
        // 寻找插入Text的位置
        final int insert_index = layout.indexOfChild(layout_for_button) + 1;

        // 使用自定义类设置点击监听器
        button_add_text.setOnClickListener(new MyClickListener(layout, button_del_text, insert_index));
        // 使用lambda表达式设置点击监听器
        button_del_text.setOnClickListener(view -> {
            // 判断是否访问越界，否则闪退
            if (layout.getChildCount() > insert_index) {
                layout.removeViewAt(insert_index);
            }
            if (layout.getChildCount() <= insert_index) {
                view.setEnabled(false);
            }
        });

        // 跳转Activity
        button_goto.setOnClickListener(view -> {
            Intent intent = new Intent(ScrollViewActivity.this, ScrollingActivity.class);
            startActivity(intent);
        });
    }

    // 实现自定义点击类
    static class MyClickListener implements View.OnClickListener {

        private final LinearLayout layout;
        private final Button button;
        private static int number = 200;
        private final int index;

        public MyClickListener(LinearLayout layout, Button button, int index) {
            this.layout = layout;
            this.button = button;
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            TextView tv = new TextView(context);
            // 设置布局参数
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(layout.getWidth(), Utils.dp2px(context, 120));
            viewParams.topMargin = Utils.dp2px(context, 20);
            tv.setLayoutParams(viewParams);
            // 设置文本
            tv.setText(String.format("Text %d", number++));
            tv.setTextSize(20);
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundColor(0xFFEECCCC);
            tv.setGravity(Gravity.CENTER);

            layout.addView(tv, index);
            button.setEnabled(true);
        }
    }
}