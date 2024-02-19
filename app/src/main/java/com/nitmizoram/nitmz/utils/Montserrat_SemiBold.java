package com.nitmizoram.nitmz.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class Montserrat_SemiBold extends AppCompatTextView {
    public Montserrat_SemiBold(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public Montserrat_SemiBold(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        applyCustomFont(context);
    }

    public Montserrat_SemiBold(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        setTypeface(FontCache.getTypeface("fonts/Montserrat_SemiBold.ttf", context));
    }
}
