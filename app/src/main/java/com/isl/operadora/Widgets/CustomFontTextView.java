package com.isl.operadora.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import com.isl.operadora.R;

/**
 * Created by webx on 08/04/15.
 */
public class CustomFontTextView extends CheckedTextView {

    public CustomFontTextView(Context context) {
        this(context, null);
    }


    public CustomFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.customFontTextViewStyle);
    }


    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTextView, defStyle, 0);

        String fontFileName = a.getString(R.styleable.CustomFontTextView_fontFileName);
        a.recycle();

        setTypeface(fontFileName);
    }


    /**
     * Sets the font. The file must be located in the assets folder.
     * @param customFontFileNameResId the resource ID that holds the
     *                                font file (extension included).
     */
    public void setTypeface(int customFontFileNameResId) {
        setTypeface(getContext().getString(customFontFileNameResId));
    }


    /**
     * Sets the font. The file must be located in the assets folder.
     * @param customFontFileName the name of the font file (extension included).
     */
    public void setTypeface(CharSequence customFontFileName) {
        if (!TextUtils.isEmpty(customFontFileName)) {
            String fontPath = "fonts/" + customFontFileName;
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), fontPath);
            setTypeface(typeface);
        }
    }
}
