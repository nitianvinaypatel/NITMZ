package com.example.nitmz.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class Montserrat_Medium extends AppCompatTextView {
    public Montserrat_Medium(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public Montserrat_Medium(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        applyCustomFont(context);
    }

    public Montserrat_Medium(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        setTypeface(FontCache.getTypeface("font/Montserrat_Medium.ttf", context));
    }
}
