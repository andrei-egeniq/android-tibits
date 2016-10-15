package com.egeniq.stylespan;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.MetricAffectingSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;

/**
 * Created by andrei on 08/10/16.
 */

public class MainActivity extends AppCompatActivity {

    private Typeface mRegularTypeface;
    private Typeface mBoldTypeface;
    private Typeface mItalicTypeface;
    private Typeface mBoldItalicTypeface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        Spanned spanned = null;

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(getString(R.string.android_html_text));
        } else {
            spanned = Html.fromHtml(getString(R.string.android_html_text), Html.FROM_HTML_MODE_COMPACT);
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(spanned);

        StyleSpan[] styleSpanArray = builder.getSpans(0, builder.length(), StyleSpan.class);
        SuperscriptSpan[] superscriptSpanArray = builder.getSpans(0, builder.length(), SuperscriptSpan.class);

        mCreateTypeface();
        mCustomiseStyleSpan(styleSpanArray, builder);
        mCustomiseScriptSpan(superscriptSpanArray, builder);

        TextView textView = (TextView)findViewById(R.id.text_view);
        textView.setTypeface(mRegularTypeface);
        textView.setText(builder, TextView.BufferType.SPANNABLE);
    }

    private void mCreateTypeface() {
        mRegularTypeface = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        mBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Caviar_Dreams_Bold.ttf");
        mItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams_Italic.ttf");
        mBoldItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams_BoldItalic.ttf");
    }

    private void mCustomiseStyleSpan(StyleSpan[] spanArray, SpannableStringBuilder builder) {
        for(StyleSpan styleSpan : spanArray) {
            int start = builder.getSpanStart(styleSpan);
            int end = builder.getSpanEnd(styleSpan);

            CalligraphyTypefaceSpan typefaceSpan = null;

            switch (styleSpan.getStyle()) {
                case Typeface.NORMAL:
                    typefaceSpan = new CalligraphyTypefaceSpan(mRegularTypeface);
                    break;
                case Typeface.BOLD:
                    typefaceSpan = new CalligraphyTypefaceSpan(mBoldTypeface);
                    break;
                case Typeface.ITALIC:
                    typefaceSpan = new CalligraphyTypefaceSpan(mItalicTypeface);
                    break;
                case Typeface.BOLD_ITALIC:
                    typefaceSpan = new CalligraphyTypefaceSpan(mBoldItalicTypeface);
                    break;
            }

            builder.removeSpan(styleSpan);
            builder.setSpan(typefaceSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void mCustomiseScriptSpan(MetricAffectingSpan[] spanArray, SpannableStringBuilder builder) {
        for(MetricAffectingSpan metricAffectingSpan : spanArray) {
            int start = builder.getSpanStart(metricAffectingSpan);
            int end = builder.getSpanEnd(metricAffectingSpan);

            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.6f);

            builder.setSpan(sizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new CalligraphyTypefaceSpan(mBoldItalicTypeface), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
