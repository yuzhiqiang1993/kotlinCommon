package com.yzq.lib_widget.drop_down_menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.yzq.lib_widget.R;


/*
 * 自定义的tabItem
 * */
public class TabItem extends LinearLayout {

    private AppCompatTextView titleTv;
    private View view;
    private AppCompatImageView tabIcon;

    public TabItem(Context context) {
        this(context, null);
    }

    public TabItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);


    }

    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_tab_item, this);
        titleTv = view.findViewById(R.id.tv_tab_title);
        tabIcon = view.findViewById(R.id.iv_tab_icon);
    }


    public void setTitle(String title) {
        titleTv.setText(title);
    }


    public void setTitleColor(int textSelectedColor) {
        titleTv.setTextColor(textSelectedColor);
    }


    public void setIcon(int menuSelectedIcon, int color) {
        tabIcon.setImageResource(menuSelectedIcon);
        tabIcon.setColorFilter(color);

    }
}
