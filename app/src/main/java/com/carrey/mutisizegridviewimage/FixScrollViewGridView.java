package com.carrey.mutisizegridviewimage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by pccai on 2018/3/7.
 */

public class FixScrollViewGridView extends GridView {
    public FixScrollViewGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixScrollViewGridView(Context context) {
        super(context);
    }

    public FixScrollViewGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
